import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class interfaz extends JFrame {
    private JTextField txtCodigo, txtNombre, txtBuscarCodigo, textField1;
    private JComboBox<String> cbPoderes, cbUniverso, cbNivel, cbResUniverso, cbResNivel, comboBox1;
    private JButton btnAgregar, btnBuscar;
    private JTable tablaHeroes;
    private DefaultTableModel modelo;
    private List<SpiderverseHero> listaHeroes = new ArrayList<>();

    public interfaz() {
        setTitle("Gestión de Spiderverse");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Panel superior para agregar héroe
        JPanel panelAgregar = new JPanel(new GridLayout(6, 2));
        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        cbPoderes = new JComboBox<>(new String[]{"Telaraña", "Invisibilidad", "Fuerza", "Velocidad"});
        cbUniverso = new JComboBox<>(new String[]{"Tierra-616", "Tierra-1610", "Tierra-928"});
        cbNivel = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        btnAgregar = new JButton("Agregar");

        panelAgregar.add(new JLabel("Código:"));
        panelAgregar.add(txtCodigo);
        panelAgregar.add(new JLabel("Nombre:"));
        panelAgregar.add(txtNombre);
        panelAgregar.add(new JLabel("Poder:"));
        panelAgregar.add(cbPoderes);
        panelAgregar.add(new JLabel("Universo:"));
        panelAgregar.add(cbUniverso);
        panelAgregar.add(new JLabel("Nivel:"));
        panelAgregar.add(cbNivel);
        panelAgregar.add(btnAgregar);

        // Panel para la tabla
        modelo = new DefaultTableModel(new String[]{"Código", "Nombre", "Poder", "Universo", "Nivel"}, 0);
        tablaHeroes = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaHeroes);

        // Panel de búsqueda
        JPanel panelBuscar = new JPanel(new GridLayout(6, 2));
        txtBuscarCodigo = new JTextField();
        textField1 = new JTextField();
        comboBox1 = new JComboBox<>(new String[]{"Telaraña", "Invisibilidad", "Fuerza", "Velocidad"});
        cbResUniverso = new JComboBox<>(new String[]{"Tierra-616", "Tierra-1610", "Tierra-928"});
        cbResNivel = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        btnBuscar = new JButton("Buscar");

        panelBuscar.add(new JLabel("Buscar código:"));
        panelBuscar.add(txtBuscarCodigo);
        panelBuscar.add(new JLabel("Nombre:"));
        panelBuscar.add(textField1);
        panelBuscar.add(new JLabel("Poder:"));
        panelBuscar.add(comboBox1);
        panelBuscar.add(new JLabel("Universo:"));
        panelBuscar.add(cbResUniverso);
        panelBuscar.add(new JLabel("Nivel:"));
        panelBuscar.add(cbResNivel);
        panelBuscar.add(btnBuscar);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Agregar", panelAgregar);
        tabs.add("Buscar", panelBuscar);

        panel.add(tabs, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(panel);

        // Listeners
        btnAgregar.addActionListener(e -> agregarHeroe());
        btnBuscar.addActionListener(e -> buscarHeroe());
    }

    private void agregarHeroe() {
        try {
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            for (SpiderverseHero hero : listaHeroes) {
                if (hero.getCodigo() == codigo) {
                    JOptionPane.showMessageDialog(null, "Código duplicado.");
                    return;
                }
            }

            String nombre = txtNombre.getText().trim();
            String poder = cbPoderes.getSelectedItem().toString();
            String universo = cbUniverso.getSelectedItem().toString();
            int nivel = Integer.parseInt(cbNivel.getSelectedItem().toString());

            SpiderverseHero nuevo = new SpiderverseHero(codigo, nombre, poder, universo, nivel);
            listaHeroes.add(nuevo);
            actualizarTabla();
            JOptionPane.showMessageDialog(null, "Héroe agregado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar: " + ex.getMessage());
        }
    }

    private void buscarHeroe() {
        try {
            int codigo = Integer.parseInt(txtBuscarCodigo.getText().trim());
            for (SpiderverseHero hero : listaHeroes) {
                if (hero.getCodigo() == codigo) {
                    textField1.setText(hero.getNombre());
                    comboBox1.setSelectedItem(hero.getPoderEspecial());
                    cbResUniverso.setSelectedItem(hero.getUniverso());
                    cbResNivel.setSelectedItem(String.valueOf(hero.getNivelExperiencia()));
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "No encontrado.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código inválido.");
        }
    }

    private void actualizarTabla() {
        modelo.setRowCount(0);
        for (SpiderverseHero hero : listaHeroes) {
            modelo.addRow(new Object[]{
                    hero.getCodigo(),
                    hero.getNombre(),
                    hero.getPoderEspecial(),
                    hero.getUniverso(),
                    hero.getNivelExperiencia()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new interfaz().setVisible(true));
    }
}
class SpiderverseHero {
    private int codigo;
    private String nombre;
    private String poderEspecial;
    private String universo;
    private int nivelExperiencia;

    public SpiderverseHero(int codigo, String nombre, String poderEspecial, String universo, int nivelExperiencia) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.poderEspecial = poderEspecial;
        this.universo = universo;
        this.nivelExperiencia = nivelExperiencia;
    }

    public int getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getPoderEspecial() { return poderEspecial; }
    public String getUniverso() { return universo; }
    public int getNivelExperiencia() { return nivelExperiencia; }
}

