package visão;

import dado.dados;
import fonoagenda.fonoagenda;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import modelo.agendamento;
import modelo.fonoaudiologo;
import modelo.paciente;

public class telaagenda extends JPanel {

    private fonoagenda janela;

    private JComboBox<paciente> comboPaciente = new JComboBox<>();
    private JComboBox<fonoaudiologo> comboFono = new JComboBox<>();
    private JTextField data = new JTextField();
    private JTextField hora = new JTextField();

    private JPanel grade = new JPanel(new GridLayout(0, 1, 0, 4));
    private JLabel erro = new JLabel(" ");

    public telaagenda(fonoagenda janela) {
        this.janela = janela;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel topo = new JPanel();
        topo.setBackground(cores.roxo);
        topo.setPreferredSize(new Dimension(0, 70));

        JLabel titulo = new JLabel("FonoAgenda - Agenda");
        titulo.setFont(cores.fonteTitulo);
        titulo.setForeground(Color.WHITE);
        topo.add(titulo);

        add(topo, BorderLayout.NORTH);

        JPanel esquerda = new JPanel(new GridLayout(5, 1, 10, 14));
        esquerda.setBackground(cores.azulClaro);
        esquerda.setBorder(new EmptyBorder(25, 25, 25, 25));

        esquerda.add(bloco("Paciente", comboPaciente));
        esquerda.add(bloco("Fonoaudiólogo", comboFono));
        esquerda.add(bloco("Data (dd/mm/aaaa)", data));
        esquerda.add(bloco("Hora (hh:mm)", hora));

        erro.setFont(cores.fonteNegrito);
        erro.setForeground(cores.erro);
        esquerda.add(erro);

        grade.setBackground(Color.WHITE);

        JPanel topoGrade = new JPanel(new BorderLayout());
        topoGrade.setBackground(Color.WHITE);
        topoGrade.add(grade, BorderLayout.NORTH);

        JScrollPane rolagem = new JScrollPane(topoGrade);
        rolagem.setBorder(new EmptyBorder(25, 25, 25, 25));

        JPanel meio = new JPanel(new GridLayout(1, 2, 20, 0));
        meio.setBackground(Color.WHITE);
        meio.add(esquerda);
        meio.add(rolagem);

        add(meio, BorderLayout.CENTER);

        comboFono.addActionListener(e -> mostrarGrade());
        data.addActionListener(e -> mostrarGrade());
        data.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                mostrarGrade();
            }
        });

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rodape.setBackground(new Color(0xF5, 0xF5, 0xF5));

        JButton cancelar = new JButton("Cancelar");
        cancelar.setFont(cores.fonteNegrito);
        cancelar.setPreferredSize(new Dimension(120, 44));
        cancelar.addActionListener(e -> janela.abrirMenu());

        JButton agendar = new JButton("Agendar");
        agendar.setFont(cores.fonteNegrito);
        agendar.setBackground(cores.roxoEscuro);
        agendar.setForeground(Color.WHITE);
        agendar.setOpaque(true);
        agendar.setPreferredSize(new Dimension(120, 44));
        agendar.addActionListener(e -> agendar());

        rodape.add(cancelar);
        rodape.add(agendar);

        add(rodape, BorderLayout.SOUTH);
    }

    private JPanel bloco(String rotulo, JComponent campo) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setBackground(cores.azulClaro);

        JLabel l = new JLabel(rotulo);
        l.setFont(cores.fonteNegrito);
        l.setForeground(cores.texto);

        campo.setFont(cores.fonte);
        campo.setPreferredSize(new Dimension(280, 34));

        p.add(l, BorderLayout.NORTH);
        p.add(campo, BorderLayout.CENTER);
        return p;
    }

    public void atualizar() {
        dados.carregar();

        comboPaciente.removeAllItems();
        for (int i = 0; i < dados.pacientes.size(); i++) {
            comboPaciente.addItem(dados.pacientes.get(i));
        }

        comboFono.removeAllItems();
        for (int i = 0; i < dados.fonoaudiologos.size(); i++) {
            comboFono.addItem(dados.fonoaudiologos.get(i));
        }

        data.setText("");
        hora.setText("");
        erro.setText(" ");
        mostrarGrade();
    }

    private LocalDate lerData() {
        try {
            String[] partes = data.getText().trim().split("/");
            int d = Integer.parseInt(partes[0].trim());
            int m = Integer.parseInt(partes[1].trim());
            int a = Integer.parseInt(partes[2].trim());
            return LocalDate.of(a, m, d);
        } catch (Exception e) {
            return null;
        }
    }

    private LocalTime lerHora() {
        try {
            String texto = hora.getText().trim().replace("h", ":").replace("H", ":");
            String[] partes = texto.split(":");

            if (partes.length > 2) {
                return null;
            }

            int hh = Integer.parseInt(partes[0].trim());
            int mm = 0;

            if (partes.length == 2) {
                mm = Integer.parseInt(partes[1].trim());
            }

            return LocalTime.of(hh, mm);
        } catch (Exception e) {
            return null;
        }
    }

    private void mostrarGrade() {
        grade.removeAll();

        fonoaudiologo f = (fonoaudiologo) comboFono.getSelectedItem();
        LocalDate dia = lerData();

        if (f == null || dia == null) {
            JLabel aviso = new JLabel("Escolha o fonoaudiólogo e digite a data");
            aviso.setFont(cores.fonte);
            grade.add(aviso);
        } else {
            for (int hh = 8; hh <= 17; hh++) {
                for (int mm = 0; mm <= 30; mm = mm + 30) {
                    LocalDateTime horario = LocalDateTime.of(dia, LocalTime.of(hh, mm));
                    String texto = String.format("%02d:%02d", hh, mm) + "  -  Livre";
                    boolean ocupado = false;

                    for (int i = 0; i < dados.agendamentos.size(); i++) {
                        agendamento ag = dados.agendamentos.get(i);

                        if (ag.getFonoaudiologo().getId() == f.getId()
                                && ag.getDataHora().equals(horario)) {
                            ocupado = true;
                            texto = String.format("%02d:%02d", hh, mm)
                                    + "  -  Ocupado (" + ag.getPaciente().getNome() + ")";
                        }
                    }

                    JLabel linha = new JLabel(texto);
                    linha.setFont(cores.fonte);
                    linha.setForeground(cores.texto);
                    linha.setOpaque(true);
                    linha.setBorder(new EmptyBorder(4, 8, 4, 8));

                    if (ocupado) {
                        linha.setBackground(new Color(0xCC, 0xCC, 0xCC));
                    } else {
                        linha.setBackground(Color.WHITE);
                    }

                    grade.add(linha);
                }
            }
        }

        grade.revalidate();
        grade.repaint();
    }

    private void agendar() {
        erro.setText(" ");

        paciente p = (paciente) comboPaciente.getSelectedItem();

        if (p == null) {
            erro.setText("⚠ Escolha um paciente");
            return;
        }

        fonoaudiologo f = (fonoaudiologo) comboFono.getSelectedItem();

        if (f == null) {
            erro.setText("⚠ Escolha um fonoaudiólogo");
            return;
        }

        if (data.getText().trim().equals("")) {
            erro.setText("⚠ Digite a data. Ex.: 15/11/2026");
            data.requestFocusInWindow();
            return;
        }

        if (hora.getText().trim().equals("")) {
            erro.setText("⚠ Digite a hora. Ex.: 14:00");
            hora.requestFocusInWindow();
            return;
        }

        LocalDate dia = lerData();

        if (dia == null) {
            erro.setText("⚠ Data inválida. Use 15/11/2026");
            data.requestFocusInWindow();
            return;
        }

        LocalTime horario = lerHora();

        if (horario == null) {
            erro.setText("⚠ Hora inválida. Use 14:00");
            hora.requestFocusInWindow();
            return;
        }

        LocalDateTime quando = LocalDateTime.of(dia, horario);

        if (dados.horarioOcupado(f, quando)) {
            erro.setText("⚠ Este horário já está ocupado");
            return;
        }

        if (dados.salvar(new agendamento(p, f, quando))) {
            JOptionPane.showMessageDialog(this, "Consulta agendada com sucesso");
            hora.setText("");
            mostrarGrade();
        }
    }
}