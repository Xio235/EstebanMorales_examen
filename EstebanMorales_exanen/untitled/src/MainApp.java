import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MainApp extends JFrame {
    private ArrayList<SpiderverseHero> heroes = new ArrayList<>();
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtCodigo, txtNombre;
    private JComboBox<String> cbPoderes, cbUniverso, cbNivel;

    public MainApp() {
        setTitle("Gestión de Héroes del Spiderverse");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Pestaña Registro
        JPanel panelRegistro = new JPanel(new BorderLayout());
        JPanel formulario = new JPanel(new GridLayout(6, 2, 10, 10));

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        cbPoderes = new JComboBox<>(new String[]{
                "Sentido Arácnido", "Trepa Muros", "Fuerza Sobrehumana", "Agilidad Mejorada", "Tejido de Telaraña"
        });
        cbUniverso = new JComboBox<>(new String[]{
                "Tierra-616", "Tierra-1610", "Tierra-12041", "Tierra-90214", "Tierra-138"
        });
        cbNivel = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});

        formulario.add(new JLabel("Código:"));
        formulario.add(txtCodigo);
        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Poder Especial:"));
        formulario.add(cbPoderes);
        formulario.add(new JLabel("Universo:"));
        formulario.add(cbUniverso);
        formulario.add(new JLabel("Nivel de Experiencia:"));
        formulario.add(cbNivel);

        JButton btnAgregar = new JButton("Agregar Héroe");
        formulario.add(btnAgregar);

        panelRegistro.add(formulario, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{
                "Código", "Nombre", "Poderes Especiales", "Universo", "Nivel de Experiencia"
        }, 0);
        tabla = new JTable(modeloTabla);
        panelRegistro.add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> agregarHeroe());

        tabbedPane.add("Registro", panelRegistro);

        // Pestaña Búsqueda
        JPanel panelBusqueda = new JPanel(new BorderLayout());
        JPanel panelBuscar = new JPanel();

        JTextField txtBuscarCodigo = new JTextField(10);
        JButton btnBuscar = new JButton("Buscar");
        panelBuscar.add(new JLabel("Código:"));
        panelBuscar.add(txtBuscarCodigo);
        panelBuscar.add(btnBuscar);

        JPanel panelResultados = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField txtResNombre = new JTextField();
        JComboBox<String> cbResPoder = new JComboBox<>(cbPoderes.getModel());
        JComboBox<String> cbResUniverso = new JComboBox<>(cbUniverso.getModel());
        JComboBox<String> cbResNivel = new JComboBox<>(cbNivel.getModel());

        panelResultados.add(new JLabel("Nombre:"));
        panelResultados.add(txtResNombre);
        panelResultados.add(new JLabel("Poder:"));
        panelResultados.add(cbResPoder);
        panelResultados.add(new JLabel("Universo:"));
        panelResultados.add(cbResUniverso);
        panelResultados.add(new JLabel("Nivel:"));
        panelResultados.add(cbResNivel);

        btnBuscar.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(txtBuscarCodigo.getText());
                SpiderverseHero heroe = buscarHeroe(codigo);
                if (heroe != null) {
                    txtResNombre.setText(heroe.getNombre());
                    cbResPoder.setSelectedItem(heroe.getPoderesEspeciales());
                    cbResUniverso.setSelectedItem(heroe.getUniverso());
                    cbResNivel.setSelectedItem(String.valueOf(heroe.getNivelExperiencia()));
                } else {
                    JOptionPane.showMessageDialog(this, "Héroe no encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Código inválido.");
            }
        });

        panelBusqueda.add(panelBuscar, BorderLayout.NORTH);
        panelBusqueda.add(panelResultados, BorderLayout.CENTER);

        tabbedPane.add("Búsqueda", panelBusqueda);

        add(tabbedPane);
    }

    private void agregarHeroe() {
        try {
            int codigo = Integer.parseInt(txtCodigo.getText());
            if (buscarHeroe(codigo) != null) {
                JOptionPane.showMessageDialog(this, "Código ya registrado.");
                return;
            }
            SpiderverseHero heroe = new SpiderverseHero(
                    codigo,
                    txtNombre.getText(),
                    (String) cbPoderes.getSelectedItem(),
                    (String) cbUniverso.getSelectedItem(),
                    Integer.parseInt((String) cbNivel.getSelectedItem())
            );
            heroes.add(0, heroe);  // Añadir al inicio
            modeloTabla.insertRow(0, new Object[]{
                    heroe.getCodigo(),
                    heroe.getNombre(),
                    heroe.getPoderesEspeciales(),
                    heroe.getUniverso(),
                    heroe.getNivelExperiencia()
            });
            limpiarFormulario();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código debe ser un número.");
        }
    }

    private SpiderverseHero buscarHeroe(int codigo) {
        for (SpiderverseHero h : heroes) {
            if (h.getCodigo() == codigo) return h;
        }
        return null;
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        cbPoderes.setSelectedIndex(0);
        cbUniverso.setSelectedIndex(0);
        cbNivel.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp().setVisible(true));
    }
}
