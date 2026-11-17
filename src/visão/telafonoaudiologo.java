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
import modelo.fonoaudiologo;

public class telafonoaudiologo extends JPanel {

    private fonoagenda janela;

    private JTextField nome = new JTextField();
    private JTextField especialidade = new JTextField();

    private JLabel erroNome = new JLabel(" ");

    private fonoaudiologo fonoAtual = null;

    public telafonoaudiologo(fonoagenda janela) {
        this.janela = janela;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel topo = new JPanel();
        topo.setBackground(cores.roxo);
        topo.setPreferredSize(new Dimension(0, 70));

        JLabel titulo = new JLabel("FonoAgenda - Cadastro de Fonoaudiólogo");
        titulo.setFont(cores.fonteTitulo);
        titulo.setForeground(Color.WHITE);
        topo.add(titulo);

        add(topo, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridLayout(2, 1, 20, 20));
        campos.setBackground(cores.azulClaro);

        campos.add(bloco("Nome", nome));
        campos.add(bloco("Especialidade", especialidade));

        JPanel formulario = new JPanel(new BorderLayout());
        formulario.setBackground(cores.azulClaro);
        formulario.setBorder(new EmptyBorder(30, 30, 30, 30));
        formulario.add(campos, BorderLayout.NORTH);

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
        fonoAtual = null;
        nome.setText("");
        especialidade.setText("");
    }

    public void editar(fonoaudiologo f) {
        erroNome.setText(" ");
        fonoAtual = f;
        nome.setText(f.getNome());
        especialidade.setText(f.getEspecialidade());
    }

    private void salvar() {
        if (nome.getText().trim().isEmpty()) {
            erroNome.setText("⚠ Campo obrigatório: Nome");
            nome.requestFocusInWindow();
            return;
        }
        erroNome.setText(" ");

        fonoaudiologo f;

        if (fonoAtual == null) {
            f = new fonoaudiologo();
        } else {
            f = fonoAtual;
        }

        f.setNome(nome.getText().trim());
        f.setEspecialidade(especialidade.getText().trim());

        if (dados.salvar(f)) {
            JOptionPane.showMessageDialog(this, "Fonoaudiólogo salvo com sucesso");
            janela.abrirMenu();
        }
    }
}