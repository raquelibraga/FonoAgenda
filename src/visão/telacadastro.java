package visão;

import dado.dados;
import fonoagenda.fonoagenda;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import modelo.fonoaudiologo;
import modelo.paciente;

public class telacadastro extends JPanel {

    private fonoagenda janela;

    private JTextField busca = new JTextField();

    private DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Nome", "Tipo", "Telefone"}, 0);

    private JTable tabela = new JTable(modelo);

    private List<Object> itens = new ArrayList<>();

    public telacadastro(fonoagenda janela) {
        this.janela = janela;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel topo = new JPanel();
        topo.setBackground(cores.roxo);
        topo.setPreferredSize(new Dimension(0, 70));

        JLabel titulo = new JLabel("FonoAgenda - Cadastrados");
        titulo.setFont(cores.fonteTitulo);
        titulo.setForeground(Color.WHITE);
        topo.add(titulo);

        add(topo, BorderLayout.NORTH);

        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        barra.setBackground(Color.WHITE);

        JLabel rotulo = new JLabel("Buscar por nome");
        rotulo.setFont(cores.fonteNegrito);

        busca.setFont(cores.fonte);
        busca.setPreferredSize(new Dimension(250, 34));
        busca.addActionListener(e -> atualizar());

        JButton buscar = new JButton("Buscar");
        buscar.setFont(cores.fonteNegrito);
        buscar.setPreferredSize(new Dimension(100, 44));
        buscar.addActionListener(e -> atualizar());

        barra.add(rotulo);
        barra.add(busca);
        barra.add(buscar);

        tabela.setFont(cores.fonte);
        tabela.setRowHeight(28);
        tabela.getTableHeader().setFont(cores.fonteNegrito);

        JPanel conteudo = new JPanel(new BorderLayout(0, 15));
        conteudo.setBackground(Color.WHITE);
        conteudo.setBorder(new EmptyBorder(20, 30, 20, 30));
        conteudo.add(barra, BorderLayout.NORTH);
        conteudo.add(new JScrollPane(tabela), BorderLayout.CENTER);

        add(conteudo, BorderLayout.CENTER);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rodape.setBackground(new Color(0xF5, 0xF5, 0xF5));

        JButton editar = new JButton("Editar");
        editar.setFont(cores.fonteNegrito);
        editar.setPreferredSize(new Dimension(120, 44));
        editar.addActionListener(e -> editar());

        JButton excluir = new JButton("Excluir");
        excluir.setFont(cores.fonteNegrito);
        excluir.setPreferredSize(new Dimension(120, 44));
        excluir.addActionListener(e -> excluir());

        JButton voltar = new JButton("Voltar ao menu");
        voltar.setFont(cores.fonteNegrito);
        voltar.setPreferredSize(new Dimension(170, 44));
        voltar.addActionListener(e -> janela.abrirMenu());

        rodape.add(editar);
        rodape.add(excluir);
        rodape.add(voltar);

        add(rodape, BorderLayout.SOUTH);
    }

    public void atualizar() {
        dados.carregar();
        modelo.setRowCount(0);
        itens.clear();

        String filtro = busca.getText().trim().toLowerCase();

        for (int i = 0; i < dados.pacientes.size(); i++) {
            paciente p = dados.pacientes.get(i);

            if (p.getNome().toLowerCase().contains(filtro)) {
                itens.add(p);
                modelo.addRow(new Object[]{p.getNome(), "Paciente", p.getTelefone()});
            }
        }

        for (int i = 0; i < dados.fonoaudiologos.size(); i++) {
            fonoaudiologo f = dados.fonoaudiologos.get(i);

            if (f.getNome().toLowerCase().contains(filtro)) {
                itens.add(f);
                modelo.addRow(new Object[]{f.getNome(), "Fonoaudiólogo", "-"});
            }
        }
    }

    private void editar() {
        int linha = tabela.getSelectedRow();

        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Escolha uma linha da tabela",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object item = itens.get(linha);

        if (item instanceof paciente) {
            janela.editarPaciente((paciente) item);
        } else {
            janela.editarFonoaudiologo((fonoaudiologo) item);
        }
    }

    private void excluir() {
        int linha = tabela.getSelectedRow();

        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Escolha uma linha da tabela",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(this, "Excluir este cadastro?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (resposta != JOptionPane.YES_OPTION) {
            return;
        }

        Object item = itens.get(linha);

        if (item instanceof paciente) {
            dados.excluir((paciente) item);
        } else {
            dados.excluir((fonoaudiologo) item);
        }

        atualizar();
    }
}