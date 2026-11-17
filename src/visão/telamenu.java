package visão;

import fonoagenda.fonoagenda;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class telamenu extends JPanel {

    public telamenu(fonoagenda janela) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel topo = new JPanel();
        topo.setBackground(cores.roxo);
        topo.setPreferredSize(new Dimension(0, 70));

        JLabel titulo = new JLabel("FonoAgenda - Menu Principal");
        titulo.setFont(cores.fonteTitulo);
        titulo.setForeground(Color.WHITE);
        topo.add(titulo);

        add(topo, BorderLayout.NORTH);

        JPanel botoes = new JPanel(new GridLayout(2, 2, 30, 30));
        botoes.setBackground(Color.WHITE);
        botoes.setBorder(new EmptyBorder(60, 120, 60, 120));

        JButton paciente = botao("Cadastrar paciente");
        paciente.addActionListener(e -> janela.abrirPaciente());

        JButton fono = botao("Cadastrar fonoaudiólogo");
        fono.addActionListener(e -> janela.abrirFonoaudiologo());

        JButton agenda = botao("Agenda");
        agenda.addActionListener(e -> janela.abrirAgenda());

        JButton cadastrados = botao("Ver cadastrados");
        cadastrados.addActionListener(e -> janela.abrirCadastrados());

        botoes.add(paciente);
        botoes.add(fono);
        botoes.add(agenda);
        botoes.add(cadastrados);

        add(botoes, BorderLayout.CENTER);
    }

    private JButton botao(String rotulo) {
        JButton b = new JButton(rotulo);
        b.setFont(new Font("Helvetica", Font.BOLD, 20));
        b.setBackground(cores.roxo);
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setPreferredSize(new Dimension(300, 120));
        return b;
    }
}