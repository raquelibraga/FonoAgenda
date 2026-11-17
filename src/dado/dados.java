package dado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.agendamento;
import modelo.fonoaudiologo;
import modelo.paciente;

public class dados {

    public static List<paciente> pacientes = new ArrayList<>();
    public static List<fonoaudiologo> fonoaudiologos = new ArrayList<>();
    public static List<agendamento> agendamentos = new ArrayList<>();

    public static void carregar() {
        try (Connection con = Conexao.getConexao();
             Statement st = con.createStatement()) {

            pacientes.clear();
            try (ResultSet rs = st.executeQuery("SELECT * FROM paciente ORDER BY nome")) {
                while (rs.next()) {
                    paciente p = new paciente();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setIdade(rs.getInt("idade"));
                    p.setQueixa(rs.getString("queixa"));
                    p.setTelefone(rs.getString("telefone"));
                    p.setEmail(rs.getString("email"));
                    p.setEndereco(rs.getString("endereco"));
                    pacientes.add(p);
                }
            }

            fonoaudiologos.clear();
            try (ResultSet rs = st.executeQuery("SELECT * FROM fonoaudiologo ORDER BY nome")) {
                while (rs.next()) {
                    fonoaudiologo f = new fonoaudiologo();
                    f.setId(rs.getInt("id"));
                    f.setNome(rs.getString("nome"));
                    f.setEspecialidade(rs.getString("especialidade"));
                    fonoaudiologos.add(f);
                }
            }

            agendamentos.clear();
            try (ResultSet rs = st.executeQuery("SELECT * FROM consulta ORDER BY data, hora")) {
                while (rs.next()) {
                    agendamento a = new agendamento();
                    a.setId(rs.getInt("id"));
                    a.setPaciente(acharPaciente(rs.getInt("paciente_id")));
                    a.setFonoaudiologo(acharFono(rs.getInt("fonoaudiologo_id")));
                    LocalDate dia = rs.getDate("data").toLocalDate();
                    LocalTime hora = rs.getTime("hora").toLocalTime();
                    a.setDataHora(LocalDateTime.of(dia, hora));
                    agendamentos.add(a);
                }
            }

        } catch (SQLException e) {
            mostrarErro("carregar os dados", e);
        }
    }

    public static boolean salvar(paciente p) {
        try (Connection con = Conexao.getConexao()) {
            if (p.getId() == 0) {
                String sql = "INSERT INTO paciente (nome, idade, queixa, telefone, email, endereco) VALUES (?,?,?,?,?,?)";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, p.getNome());
                    ps.setInt(2, p.getIdade());
                    ps.setString(3, p.getQueixa());
                    ps.setString(4, p.getTelefone());
                    ps.setString(5, p.getEmail());
                    ps.setString(6, p.getEndereco());
                    ps.executeUpdate();
                }
            } else {
                String sql = "UPDATE paciente SET nome=?, idade=?, queixa=?, telefone=?, email=?, endereco=? WHERE id=?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, p.getNome());
                    ps.setInt(2, p.getIdade());
                    ps.setString(3, p.getQueixa());
                    ps.setString(4, p.getTelefone());
                    ps.setString(5, p.getEmail());
                    ps.setString(6, p.getEndereco());
                    ps.setInt(7, p.getId());
                    ps.executeUpdate();
                }
            }
            carregar();
            return true;
        } catch (SQLException e) {
            mostrarErro("salvar o paciente", e);
            return false;
        }
    }

    public static boolean salvar(fonoaudiologo f) {
        try (Connection con = Conexao.getConexao()) {
            if (f.getId() == 0) {
                String sql = "INSERT INTO fonoaudiologo (nome, especialidade) VALUES (?,?)";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, f.getNome());
                    ps.setString(2, f.getEspecialidade());
                    ps.executeUpdate();
                }
            } else {
                String sql = "UPDATE fonoaudiologo SET nome=?, especialidade=? WHERE id=?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, f.getNome());
                    ps.setString(2, f.getEspecialidade());
                    ps.setInt(3, f.getId());
                    ps.executeUpdate();
                }
            }
            carregar();
            return true;
        } catch (SQLException e) {
            mostrarErro("salvar o fonoaudiólogo", e);
            return false;
        }
    }

    public static boolean salvar(agendamento a) {
        if (a.getId() != 0) {
            return true;
        }
        String sql = "INSERT INTO consulta (paciente_id, fonoaudiologo_id, data, hora) VALUES (?,?,?,?)";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, a.getPaciente().getId());
            ps.setInt(2, a.getFonoaudiologo().getId());
            ps.setDate(3, java.sql.Date.valueOf(a.getDataHora().toLocalDate()));
            ps.setTime(4, java.sql.Time.valueOf(a.getDataHora().toLocalTime()));
            ps.executeUpdate();
            carregar();
            return true;
        } catch (SQLException e) {
            mostrarErro("salvar a consulta", e);
            return false;
        }
    }

    public static boolean excluir(paciente p) {
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement("DELETE FROM paciente WHERE id = ?")) {
            ps.setInt(1, p.getId());
            ps.executeUpdate();
            carregar();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null,
                    "Não é possível excluir este paciente porque ele tem consultas marcadas.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return false;
        } catch (SQLException e) {
            mostrarErro("excluir o paciente", e);
            return false;
        }
    }

    public static boolean excluir(fonoaudiologo f) {
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement("DELETE FROM fonoaudiologo WHERE id = ?")) {
            ps.setInt(1, f.getId());
            ps.executeUpdate();
            carregar();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null,
                    "Não é possível excluir este fonoaudiólogo porque ele tem consultas marcadas.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return false;
        } catch (SQLException e) {
            mostrarErro("excluir o fonoaudiólogo", e);
            return false;
        }
    }

    public static boolean horarioOcupado(fonoaudiologo fono, LocalDateTime dataHora) {
        String sql = "SELECT COUNT(*) FROM consulta WHERE fonoaudiologo_id = ? AND data = ? AND hora = ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, fono.getId());
            ps.setDate(2, java.sql.Date.valueOf(dataHora.toLocalDate()));
            ps.setTime(3, java.sql.Time.valueOf(dataHora.toLocalTime()));
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            mostrarErro("conferir o horário", e);
            return true;
        }
    }

    private static paciente acharPaciente(int id) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId() == id) {
                return pacientes.get(i);
            }
        }
        return null;
    }

    private static fonoaudiologo acharFono(int id) {
        for (int i = 0; i < fonoaudiologos.size(); i++) {
            if (fonoaudiologos.get(i).getId() == id) {
                return fonoaudiologos.get(i);
            }
        }
        return null;
    }

    private static void mostrarErro(String acao, SQLException e) {
        JOptionPane.showMessageDialog(null,
                "Erro ao " + acao + ": " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
    }
}