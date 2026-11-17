package visão;

import dado.dados;
import fonoagenda.fonoagenda;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import modelo.paciente;

public class telapaciente extends JPanel {

    private fonoagenda janela;

    private JTextField nome = new JTextField();
    private JTextField idade = new JTextField();
    private JTextField queixa = new JTextField();
    private JTextField telefone = new JTextField();
    private JTextField email = new JTextField();
    private JTextField endereco = new JTextField();

    private JLabel erroNome = new JLabel(" ");

    private paciente pacienteAtual = null;

    public telapaciente(fonoagenda janela) {
        this.janela = janela;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel topo = new JPanel();
        topo.setBackground(cores.roxo);
        topo.setPreferredSize(new Dimension(0, 70));

        JLabel titulo = new JLabel("FonoAgenda - Cadastro de Paciente");
        titulo.setFont(cores.fonteTitulo);
        titulo.setForeground(Color.WHITE);
        topo.add(titulo);

        add(topo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(3, 2, 20, 20));
        formulario.setBackground(cores.azulClaro);
        formulario.setBorder(new EmptyBorder(30, 30, 30, 30));

        formulario.add(bloco("Nome", nome));
        formulario.add(bloco("Idade", idade));
        formulario.add(bloco("Queixa", queixa));
        formulario.add(bloco("Telefone", telefone));
        formulario.add(bloco("E-mail", email));
        formulario.add(bloco("Endereço", endereco));

        add(formulario, BorderLayout.CENTER);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rodape.setBackground(new Color(0xF5, 0xF5, 0xF5));

        JButton cancelar = new JButton("Cancelar");
        cancelar.setFont(cores.fonteNegrito);
        cancelar.setPreferredSize(new Dimension(120, 44));
        cancelar.addActionListener(e -> janela.abrirMenu());

        JButton salvar = new JButton("Salvar");
        salvar.setFont(cores.fonteNegrito);
        salvar.setBackground(cores.roxoEscuro);
        salvar.setForeground(Color.WHITE);
        salvar.setOpaque(true);
        salvar.setPreferredSize(new Dimension(120, 44));
        salvar.addActionListener(e -> salvar());

        rodape.add(cancelar);
        rodape.add(salvar);

        add(rodape, BorderLayout.SOUTH);
    }

    private JPanel bloco(String rotulo, JTextField campo) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setBackground(cores.azulClaro);

        JLabel l = new JLabel(rotulo);
        l.setFont(cores.fonteNegrito);
        l.setForeground(cores.texto);

        campo.setFont(cores.fonte);
        campo.setPreferredSize(new Dimension(300, 34));
        campo.setBorder(BorderFactory.createLineBorder(cores.azulBorda));

        p.add(l, BorderLayout.NORTH);
        p.add(campo, BorderLayout.CENTER);

        if (campo == nome) {
            erroNome.setFont(cores.fonteNegrito);
            erroNome.setForeground(cores.erro);
            p.add(erroNome, BorderLayout.SOUTH);
        }

        return p;
    }

    public void novo() {
        erroNome.setText(" ");
        pacienteAtual = null;
        nome.setText("");
        idade.setText("");
        queixa.setText("");
        telefone.setText("");
        email.setText("");
        endereco.setText("");
    }

    public void editar(paciente p) {
        erroNome.setText(" ");
        pacienteAtual = p;
        nome.setText(p.getNome());
        idade.setText(String.valueOf(p.getIdade()));
        queixa.setText(p.getQueixa());
        telefone.setText(p.getTelefone());
        email.setText(p.getEmail());
        endereco.setText(p.getEndereco());
    }

    private void salvar() {
        if (nome.getText().trim().isEmpty()) {
            erroNome.setText("⚠ Campo obrigatório: Nome");
            nome.requestFocusInWindow();
            return;
        }
        erroNome.setText(" ");

        int valorIdade = 0;

        if (!idade.getText().trim().isEmpty()) {
            try {
                valorIdade = Integer.parseInt(idade.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "A idade precisa ser um número",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        paciente p;

        if (pacienteAtual == null) {
            p = new paciente();
        } else {
            p = pacienteAtual;
        }

        p.setNome(nome.getText().trim());
        p.setIdade(valorIdade);
        p.setQueixa(queixa.getText().trim());
        p.setTelefone(telefone.getText().trim());
        p.setEmail(email.getText().trim());
        p.setEndereco(endereco.getText().trim());

        if (dados.salvar(p)) {
            JOptionPane.showMessageDialog(this, "Paciente salvo com sucesso");
            janela.abrirMenu();
        }
    }
}