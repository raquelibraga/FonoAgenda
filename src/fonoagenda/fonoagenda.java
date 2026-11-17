package fonoagenda;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import modelo.fonoaudiologo;
import modelo.paciente;
import visão.telaagenda;
import visão.telacadastro;
import visão.telafonoaudiologo;
import visão.telamenu;
import visão.telapaciente;

public class fonoagenda extends JFrame {

    private CardLayout cartoes = new CardLayout();
    private JPanel area = new JPanel(cartoes);

    private telamenu menu;
    private telapaciente tPaciente;
    private telafonoaudiologo tFono;
    private telaagenda tAgenda;
    private telacadastro tCadastro;

    public fonoagenda() {
        setTitle("FonoAgenda");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 640));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        menu = new telamenu(this);
        tPaciente = new telapaciente(this);
        tFono = new telafonoaudiologo(this);
        tAgenda = new telaagenda(this);
        tCadastro = new telacadastro(this);

        area.add(menu, "menu");
        area.add(tPaciente, "paciente");
        area.add(tFono, "fono");
        area.add(tAgenda, "agenda");
        area.add(tCadastro, "cadastro");

        add(area);
        abrirMenu();
    }

    public void abrirMenu() {
        cartoes.show(area, "menu");
    }

    public void abrirPaciente() {
        tPaciente.novo();
        cartoes.show(area, "paciente");
    }

    public void abrirFonoaudiologo() {
        tFono.novo();
        cartoes.show(area, "fono");
    }

    public void abrirAgenda() {
        tAgenda.atualizar();
        cartoes.show(area, "agenda");
    }

    public void abrirCadastrados() {
        tCadastro.atualizar();
        cartoes.show(area, "cadastro");
    }

    public void editarPaciente(paciente p) {
        tPaciente.editar(p);
        cartoes.show(area, "paciente");
    }

    public void editarFonoaudiologo(fonoaudiologo f) {
        tFono.editar(f);
        cartoes.show(area, "fono");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new fonoagenda().setVisible(true));
    }
}