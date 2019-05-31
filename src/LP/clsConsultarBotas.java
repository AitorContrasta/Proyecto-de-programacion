package LP;


import java.awt.Dimension;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import COMUN.itfProperty;
import LN.clsGestor;
import javax.swing.JLabel;

import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import static LP.clsConstantesLP.BTN_CONSULTAR_POR_PRECIO;
import static LP.clsConstantesLP.BTN_CONSULTAR_POR_MARCA;
import static LP.clsConstantesLP.BTN_CONSULTAR_SIN_ORDENAR;
import static COMUN.clsConstantes.Color;
import static COMUN.clsConstantes.Marca;
import static COMUN.clsConstantes.Material;
import static COMUN.clsConstantes.Modelo;
import static COMUN.clsConstantes.Precio;
import static COMUN.clsConstantes.Suela;
import static COMUN.clsConstantes.TallaBota;
import static COMUN.clsConstantes.Id;
import static LP.clsConstantesLP.BTN_ATRAS;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

/**
 * 
 * @author Aitor Contrasta y Asier Sojo
 *
 */

public class clsConsultarBotas extends JFrame implements WindowListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<itfProperty> miListaBotas;
	private JTable table;
	private JPanel contentPane;
	private clsGestor objGestor;

	/**
	 * Este JFrame nos muestra 3 botones que podemos pulsar. Uno para ver las botas
	 * que tebemos almacenadas en la base de datos sin ningun tipo de orden, es
	 * decir, al ritmo que las hemos ido insertando, otro para que nos las muestre
	 * ordenadas por precio y el ultimo boton es para volver atras.
	 * 
	 * @param _objGestor
	 */

	public clsConsultarBotas(clsGestor _objGestor) {
		setTitle("CONSULTAR BOTA");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				"Imagenes\\Caja.png"));

		objGestor = _objGestor;
		this.addWindowListener(this);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 510, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("\u00BFDE QU\u00C9 MANERA DESEA CONSULTARLAS?");
		lblTitulo.setForeground(java.awt.Color.BLACK);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(40, 11, 414, 25);
		contentPane.add(lblTitulo);

		// Crear el boton para ir atras.

		JButton btnAtras = new JButton("");
		btnAtras.setIcon(new ImageIcon("Imagenes\\Atras.png"));
		btnAtras.addKeyListener(new KeyAdapter() {

			/**
			 * Metodo para utilizar un boton pulsando ENTER
			 */

			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					clsMenuConsultar MenuConsultar = new clsMenuConsultar(objGestor);
					MenuConsultar.setVisible(true);
					MenuConsultar.setLocationRelativeTo(null);
					dispose();

				}
			}
		});
		btnAtras.setActionCommand(BTN_ATRAS);
		btnAtras.setOpaque(false);
		btnAtras.setContentAreaFilled(false);
		btnAtras.setBorderPainted(false);
		btnAtras.addActionListener(this);
		btnAtras.setBounds(0, 270, 48, 41);
		contentPane.add(btnAtras);

		// Crear el boton ordenadas.

		JButton btnOrdenadasPrecio = new JButton("<html> ORDENADAS </br> POR PRECIO </html>");
		btnOrdenadasPrecio.addKeyListener(new KeyAdapter() {

			/**
			 * Metodo para utilizar un boton pulsando ENTER
			 */

			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					MostrarBotasPorPrecio(objGestor);

				}

			}
		});
		btnOrdenadasPrecio.setActionCommand(BTN_CONSULTAR_POR_PRECIO);
		btnOrdenadasPrecio.addActionListener(this);
		btnOrdenadasPrecio.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnOrdenadasPrecio.setBounds(31, 44, 120, 48);
		contentPane.add(btnOrdenadasPrecio);

		// Crear el boton sinOrdenar.

		JButton btnSinOrdenar = new JButton("SIN ORDENAR");
		btnSinOrdenar.addKeyListener(new KeyAdapter() {

			/**
			 * Metodo para utilizar un boton pulsando ENTER
			 */

			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					MostrarBotas(objGestor);

				}

			}
		});
		btnSinOrdenar.setActionCommand(BTN_CONSULTAR_SIN_ORDENAR);
		btnSinOrdenar.addActionListener(this);
		btnSinOrdenar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSinOrdenar.setBounds(187, 55, 120, 23);
		contentPane.add(btnSinOrdenar);

		JButton btnOrdenadarMarca = new JButton("<html> ORDENADAS </br> POR MARCA </html>");
		btnOrdenadarMarca.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnOrdenadarMarca.setActionCommand(BTN_CONSULTAR_POR_MARCA);
		btnOrdenadarMarca.addActionListener(this);
		btnOrdenadarMarca.setBounds(334, 44, 120, 47);
		contentPane.add(btnOrdenadarMarca);

		JLabel fondoConsultarTodos = new JLabel("");
		fondoConsultarTodos.setIcon(new ImageIcon(
				"Imagenes\\FondoConsultarTodos.jpg"));
		fondoConsultarTodos.setBounds(0, 0, 494, 311);
		contentPane.add(fondoConsultarTodos);
	}

	public void CrearTabla() {

		table = null;

		miListaBotas = MostrarBotas(objGestor);

		clsTablaBotas tcm = new clsTablaBotas(miListaBotas);

		table = new JTable(tcm);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setEnabled(true);
		table.setRowSelectionAllowed(true);
		tcm.fireTableDataChanged();

		JScrollPane jspBotas = new JScrollPane(table);
		jspBotas.setBounds(18, 100, 457, 164);
		getContentPane().add(jspBotas);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(
				"Imagenes\\FondoConsultarTodos.jpg"));
		label.setBounds(-61, 0, 600, 311);
		contentPane.add(label);
		tcm.setData(miListaBotas);
	}

	public void CrearTablaOrdenadaPrecio() {

		table = null;

		miListaBotas = MostrarBotasPorPrecio(objGestor);

		clsTablaBotas tcm = new clsTablaBotas(miListaBotas);

		table = new JTable(tcm);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setEnabled(true);
		table.setRowSelectionAllowed(true);
		tcm.fireTableDataChanged();

		JScrollPane jspBotas = new JScrollPane(table);
		jspBotas.setBounds(18, 100, 457, 164);
		getContentPane().add(jspBotas);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(
				"Imagenes\\FondoConsultarTodos.jpg"));
		label.setBounds(-61, 0, 600, 311);
		contentPane.add(label);
		tcm.setData(miListaBotas);
	}

	public void CrearTablaOrdenadaMarca() {

		table = null;

		miListaBotas = MostrarBotasPorMarca(objGestor);

		clsTablaBotas tcm = new clsTablaBotas(miListaBotas);

		table = new JTable(tcm);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setEnabled(true);
		table.setRowSelectionAllowed(true);
		tcm.fireTableDataChanged();

		JScrollPane jspBotas = new JScrollPane(table);
		jspBotas.setBounds(18, 100, 457, 164);
		getContentPane().add(jspBotas);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(
				"Imagenes\\FondoConsultarTodos.jpg"));
		label.setBounds(-61, 0, 600, 311);
		contentPane.add(label);
		tcm.setData(miListaBotas);
	}

	/**
	 * Este es el metodo actionPerformed el cual se autogenera cuando implementamos
	 * ActionListener en la clase. En este caso hace de escuchador para los 3
	 * botones que tenemos, el de mostrar las botas que tenemos en la base de datos
	 * ordenadas por precio, donde llama al objGestor y a su vez al metodo
	 * MostrarBotasOrdenadas que nos muestra las botas que hemos intruducido de
	 * manera ordenada, el de mostrar las botas pero sin ordenar, como han ido
	 * entrando donde llama al objGestor y a su vez al metodo MostrarBotas que nos
	 * muestra las botas sin estar ordenadas y el de atras.
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {

		case BTN_CONSULTAR_POR_PRECIO:

			CrearTablaOrdenadaPrecio();
			MostrarBotasPorPrecio(objGestor);

			break;

		case BTN_CONSULTAR_POR_MARCA:

			CrearTablaOrdenadaMarca();
			MostrarBotasPorMarca(objGestor);

			break;

		case BTN_CONSULTAR_SIN_ORDENAR:

			CrearTabla();
			MostrarBotas(objGestor);

			break;

		case BTN_ATRAS:

			clsMenuConsultar MenuConsultar = new clsMenuConsultar(objGestor);
			MenuConsultar.setVisible(true);
			MenuConsultar.setLocationRelativeTo(null);
			dispose();

			break;
		}

	}

	private static ArrayList<itfProperty> MostrarBotas(clsGestor objGestor) {

		/**
		 * Este m�todo es el que permite mostrar las botas que tenemos diponibes al
		 * usuario. Mediante el ArrayList de itfProperty conseguimos sacar todos los
		 * atributos de cada prenta y mostrarselos al usuario por pantalla.
		 */

		ArrayList<itfProperty> misBotas;
		misBotas = objGestor.DameBotas();
		@SuppressWarnings("unused")
		String lista = null;

		for (itfProperty b : misBotas)
			lista += ("\n�BOTA: " + b.getProperty(Id) + " -- " + b.getProperty(Marca) + " -- " + b.getProperty(Modelo)
					+ " -- " + b.getProperty(Color) + " -- " + b.getProperty(Suela) + " -- " + b.getProperty(TallaBota)
					+ " -- " + b.getProperty(Material) + " -- " + b.getProperty(Precio));
		return misBotas;

	}

	private static ArrayList<itfProperty> MostrarBotasPorPrecio(clsGestor objGestor) {

		/**
		 * Este m�todo es el que permite mostras las botas que tenemos diponibes al
		 * usuario, pero esta vez a diferencia de la anterior, las sacaremos ordenadas
		 * por talla. Mediante el ArrayList de itfProperty conseguimos sacar todos los
		 * atributos de cada prenta y mostrarselos al usuario por pantalla.
		 * 
		 * Como hemos comentado antes, esta funci�n no est� disponible todav�a porque no
		 * conseguimos solucionar un problemas que nos da la clase clsBotas.
		 */

		ArrayList<itfProperty> misBotas;
		misBotas = objGestor.DameBotasOrdenadas();
		@SuppressWarnings("unused")
		String lista = null;

		for (itfProperty b : misBotas)
			lista += ("\n�BOTA: " + b.getProperty(Id) + " -- " + b.getProperty(Marca) + " -- " + b.getProperty(Modelo)
					+ " -- " + b.getProperty(Color) + " -- " + b.getProperty(Suela) + " -- " + b.getProperty(TallaBota)
					+ " -- " + b.getProperty(Material) + " -- " + b.getProperty(Precio));
		return misBotas;

	}

	private static ArrayList<itfProperty> MostrarBotasPorMarca(clsGestor objGestor) {

		ArrayList<itfProperty> misBotas;
		misBotas = objGestor.DameBotasOrdenadosCompareTo();
		@SuppressWarnings("unused")
		String lista = null;

		for (itfProperty b : misBotas)
			lista += ("\n�BOTA: " + b.getProperty(Id) + " -- " + b.getProperty(Marca) + " -- " + b.getProperty(Modelo)
					+ " -- " + b.getProperty(Color) + " -- " + b.getProperty(Suela) + " -- " + b.getProperty(TallaBota)
					+ " -- " + b.getProperty(Material) + " -- " + b.getProperty(Precio));
		return misBotas;
	}

	@Override
	public void windowOpened(WindowEvent e) {

	}

	/**
	 * Este metodo sirve para que cuando le damos a la X estando en el formulario no
	 * se termine la aplicacion directamente, sino que vaya a la anterior pesata�a.
	 * Para que esta funcion ocurra en la closeOperation del JFrame hay que ponerle
	 * el DISPOSE_ON_CLOSE. Esto funciona gracias a implementar el WindowListener y
	 * los metodos se autogeneran.
	 */

	@Override
	public void windowClosing(WindowEvent e) {

		clsMenuConsultar MenuConsultar = new clsMenuConsultar(objGestor);
		MenuConsultar.setVisible(true);
		MenuConsultar.setLocationRelativeTo(null);
	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}

}
