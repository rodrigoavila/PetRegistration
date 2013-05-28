import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import model.Registry;

import java.util.*;


class PetRegistration implements ActionListener {
	JPanel topPanel,bottomPanel;
	JScrollPane scrollPane;
	JFrame frame;
	
	JMenuBar menubar = new JMenuBar(); ;
	JMenu menu = new JMenu();
	JMenuItem menuItem;
	
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	int screenHeight = screenSize.height;
	int screenWidth = screenSize.width;	
	
	@SuppressWarnings("deprecation")
	PetRegistration(){
		
		frame = new JFrame("Registros de Animais");
		frame.setSize(680,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(screenWidth/4, screenHeight/4);		
		addWidgets();
		frame.show();
	}	
	
	public void addWidgets(){
		menubar.add(menu);
		
		menu = new JMenu("Menu Principal");
		
		menuItem = new JMenuItem("Novo Registro");
		menu.add(menuItem);
		menuItem.addActionListener(this);
		
		menuItem = new JMenuItem("Organizar Registros");
		menu.add(menuItem);
		menuItem.addActionListener(this);
		
		menuItem = new JMenuItem("Ver Registros");
		menu.add(menuItem);
		menuItem.addActionListener(this);

		menuItem = new JMenuItem("Salvar Registros");
		menu.add(menuItem);
		menuItem.addActionListener(this);		
		
		menubar.add(menu);
		
		frame.setJMenuBar(menubar);
		
		
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		
		
		JButton NovoRegistro = new JButton("Novo Registro");		
		JButton OrganizarRegistros = new JButton("Organizar Registros");
		JButton VerRegistros = new JButton("Ver Registros");
		JButton SalvarRegistros = new JButton("Salvar Registros");
		
		JLabel label = new JLabel("<HTML><FONT FACE = ARIAL SIZE = 2><B>Use as opções do Menu ou dos Botões para Registar");
		
		//Add Action Listeners
		NovoRegistro.addActionListener(this);		
		OrganizarRegistros.addActionListener(this);
		VerRegistros.addActionListener(this);
		SalvarRegistros.addActionListener(this);
			
		topPanel.add(label);

		bottomPanel.add(NovoRegistro);		
		bottomPanel.add(OrganizarRegistros);
		bottomPanel.add(VerRegistros);
		bottomPanel.add(SalvarRegistros);
		
		frame.getContentPane().add(topPanel,BorderLayout.NORTH);
		frame.getContentPane().add(bottomPanel,BorderLayout.SOUTH);
		frame.setResizable(false);
	}
	
	public static void main(String args[]){
		@SuppressWarnings("unused")
		PetRegistration add = new PetRegistration();		
	}	
	
	OperationHandler oh = new OperationHandler();
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getActionCommand() == "Novo Registro"){
			oh.NovoRegistro();			
		}else  if(ae.getActionCommand() == "Organizar Registros"){
			oh.OrganizarRegistros();
		}else  if(ae.getActionCommand() == "Ver Registros"){
			oh.VerRegistros();
		}else  if(ae.getActionCommand() == "Salvar Registros"){
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("My Documents"));			
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.showSaveDialog(frame);
			FileOutputStream  bfout = null;
			FileInputStream bfin = null;
			String filename= null;
			
			int p;
			
			try{
				filename = chooser.getSelectedFile().getPath();
			}catch(Exception e){
			}
			
			try{
				bfout = new FileOutputStream(filename + "registros.txt");
			}catch(Exception e){
			}
			
			try{
				bfin = new FileInputStream("registros.txt");
			}catch(Exception e){
			}

			try{
				do{
					p = bfin.read();
					if(p!=-1)
						bfout.write(p);
				}while(p!=-1);
			}catch(Exception e){
			}
		}
	}
}

class OperationHandler implements ListSelectionListener, ActionListener, Runnable{
		
	JFrame newFrame;
	JTextField txtTipoPet;
	JTextField txtNomeDono;
	JTextField txtNomePet;
	JTextField txtRaca;
	JTextField txtEndereco;
	JTextField txtTelefone;
	JTextField txtValorServico;
	JTextField txtData;
	
	JButton BttnSaveAdded;
	
	@SuppressWarnings("rawtypes")
	Vector v = new Vector(10,3);
	int i=0,k=0;
	
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	int screenHeight = screenSize.height;
	int screenWidth = screenSize.width;
	
	FileInputStream fis;
	ObjectInputStream ois;
	
	JList list;
	DefaultListModel listModel;
	ListSelectionModel listSelectionModel;
	
	JRadioButton bytpet, bynpet;
	
	Thread t;
	
	JTable searchTable;
	
	JTextField txtSearch;
	
	String columnNames[] = { "Tipo Pet", "Nome Dono", "Nome Pet", "Raca", "Endereco", "Telefone", "Valor Sevico", "Data" };
	
	Object data[][]= new Object[5][8];
	
	@SuppressWarnings("rawtypes")
	OperationHandler(){
		
		try {
			fis = new FileInputStream("registros.txt");
			ois = new ObjectInputStream(fis);
			v = (Vector) ois.readObject();
			ois.close();
		}catch(Exception e){
		}
		
	}
		
	public void run(){
		
		try{
			FileOutputStream fos = new
			FileOutputStream("registros.txt");
			ObjectOutputStream oos = new 
			ObjectOutputStream(fos);
			oos.writeObject(v);
			oos.flush();
			oos.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(newFrame, "Erro ao abrir arquivo de dados: Não foi possivel salvar conteudo.", "Erro ao abrir arquivo de dados", JOptionPane.INFORMATION_MESSAGE);
		}
	}	
	
	@SuppressWarnings("deprecation")
	public void NovoRegistro(){
		newFrame = new JFrame("Novo Registro");
		newFrame.setSize(220,250);
		newFrame.setResizable(false);		
		
		JLabel lblTipoPet = new JLabel("Tipo Pet: ");
		JLabel lblNomeDono = new JLabel("Nome Dono: ");
		JLabel lblNomePet = new JLabel("Nome Pet: ");
		JLabel lblRaca = new JLabel("Raça: ");
		JLabel lblEndereco = new JLabel("Endereco: ");
		JLabel lblTelefone = new JLabel("Telefone: ");
		JLabel lblValorServico = new JLabel("ValorServico: ");
		JLabel lblData = new JLabel("Data: ");
		@SuppressWarnings("unused")
		JLabel lblEmpty1 = new JLabel("");
		@SuppressWarnings("unused")
		JLabel lblEmpty2 = new JLabel("");
		
		txtTipoPet = new JTextField(20);
		txtNomeDono = new JTextField(20);
		txtNomePet = new JTextField(20);
		txtRaca = new JTextField(20);
		txtEndereco = new JTextField(60);
		txtTelefone = new JTextField(20);
		txtValorServico = new JTextField(20);
		txtData = new JTextField(20);
		
		JButton BttnAdd = new JButton("Novo!");
		BttnSaveAdded = new JButton("Salvo!");
		
		BttnAdd.addActionListener(this);
		BttnSaveAdded.addActionListener(this);
		BttnSaveAdded.setEnabled(true);
		
		
		JPanel centerPane = new JPanel();
		JPanel bottomPane = new JPanel();
		
		centerPane.add(lblTipoPet);
		centerPane.add(txtTipoPet);
		centerPane.add(lblNomeDono);
		centerPane.add(txtNomeDono);
		centerPane.add(lblNomePet);
		centerPane.add(txtNomePet);
		centerPane.add(lblRaca);
		centerPane.add(txtRaca);
		centerPane.add(lblEndereco);
		centerPane.add(txtEndereco);
		centerPane.add(lblTelefone);
		centerPane.add(txtTelefone);
		centerPane.add(lblValorServico);
		centerPane.add(txtValorServico);
		centerPane.add(lblData);
		centerPane.add(txtData);
		bottomPane.add(BttnAdd);
		bottomPane.add(BttnSaveAdded);
		
		centerPane.setLayout(new GridLayout(0,2));	
		
		newFrame.getContentPane().add(centerPane,BorderLayout.CENTER);		
		newFrame.getContentPane().add(bottomPane,BorderLayout.SOUTH);
		newFrame.setLocation(screenWidth/4, screenHeight/4);
		newFrame.show();
		
	}	
	
	@SuppressWarnings("deprecation")
	public void OrganizarRegistros(){
		newFrame = new JFrame("Organizar Registros");
		newFrame.setSize(250,160);
		newFrame.setLocation(screenWidth/4, screenHeight/4);		
		newFrame.setResizable(false);
		
		bytpet = new JRadioButton("Pelo Tipo de Pet");
		bytpet.setActionCommand("Pelo Tipo de Pet");
		bytpet.setSelected(true);
		
		bynpet = new JRadioButton("Pelo Nome do Pet");
		bynpet.setActionCommand("Pelo Nome do Pet");
		
		ButtonGroup group = new ButtonGroup();
		group.add(bytpet);
		group.add(bynpet);
		
		JPanel topPane = new JPanel();
		JLabel label = new JLabel("Organizar Registros Por:");
		topPane.add(label);
		
		JPanel pane = new JPanel(new GridLayout(0,1));
		pane.add(bytpet);
		pane.add(bynpet);
		
		JPanel bottomPane = new JPanel();
		JButton sortBttn = new JButton("Organizar Registros");
		JButton cancelBttn = new JButton("Cancelar");
		bottomPane.add(sortBttn);
		bottomPane.add(cancelBttn);
		sortBttn.addActionListener(this);
		cancelBttn.addActionListener(this);
		
		newFrame.getContentPane().add(topPane, BorderLayout.NORTH);
		newFrame.getContentPane().add(pane, BorderLayout.CENTER);
		newFrame.getContentPane().add(bottomPane, BorderLayout.SOUTH);
		newFrame.show();
		
	}
	
	@SuppressWarnings("deprecation")
	public void VerRegistros(){
		
		newFrame = new JFrame("Ver todos os Registros Cadastrados");
		newFrame.setSize(600,300);		
		
		Registry con = new Registry();
				
		String columnNames[] = { "Tipo Pet", "Nome Dono", "Nome Pet", "Raca", "Endereco", "Telefone", "Valor Sevico", "Data" };
				
		Object data[][]= new Object[v.size()][8];
		
		for(int j=0;j<v.size();j++){
			
			con = (Registry) v.elementAt(k);
			
			data[j][0] = con.getTipoPet();
			data[j][1] = con.getNomeDono();
			data[j][2] = con.getNomePet();
			data[j][3] = con.getRaca();
			data[j][4] = con.getEndereco();
			data[j][5] = con.getTelefone();
			data[j][6] = con.getValorServico();
			// data[j][7] = con.getData();
			
			k++;
			
		}
		
		k=0;
		
		JTable abtable = new JTable(data,columnNames);
		JScrollPane scrollPane = new JScrollPane(abtable);
		abtable.setPreferredScrollableViewportSize(new Dimension(500, 370));
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Lista de Registros Atualmente no Banco de Dados");
		pane.add(label);
		
		newFrame.getContentPane().add(pane,BorderLayout.SOUTH);
		newFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		newFrame.setLocation(screenWidth/4, screenHeight/4);
		newFrame.show();
		
		
		
		
	}	
	
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent ae){
				
		if(ae.getActionCommand() == "Novo!"){
			
			if(txtTipoPet.getText().equals("") && txtNomeDono.getText().equals("") && txtNomePet.getText().equals("") &&
			   txtRaca.getText().equals("") && txtEndereco.getText().equals("") && txtTelefone.getText().equals("") && 
			   txtValorServico.getText().equals("") && txtData.getText().equals("")){
				
			JOptionPane.showMessageDialog(newFrame,"Preencha os Campos! Preencher campos vazios.", "Preencha os Campos", JOptionPane.INFORMATION_MESSAGE);
			
			}else{
				Registry registry = new Registry();
				
				// registry.setDetails(txtTipoPet.getText(),txtNomeDono.getText(),txtNomePet.getText(),txtRaca.getText(),txtEndereco.getText(),txtTelefone.getText(),txtValorServico.getText(),txtData.getText());
				v.addElement(registry);
				txtTipoPet.setText("");
				txtNomeDono.setText("");
				txtNomePet.setText("");
				txtRaca.setText("");
				txtEndereco.setText("");
				txtTelefone.setText("");
				txtValorServico.setText("");
				txtData.setText("");
				
				if(BttnSaveAdded.isEnabled() == false)
					BttnSaveAdded.setEnabled(true);
			}		
			
		}else if(ae.getActionCommand() == "Salvo!"){
			
			saveVector();
			newFrame.setVisible(false);
					
		}else if(ae.getActionCommand() == "Ok"){
			newFrame.setVisible(false);
			
		}else if(ae.getActionCommand() == "Organizar Registros"){
			
			if(bytpet.isSelected()){
				Registry registry1 = new Registry();
				Registry registry2 = new Registry();
				Registry temp = new Registry();
				int l,m;
				
				for(l=0;l<v.size()-1;l++){
					for(m=l+1;m<v.size();m++){
						registry1 = (Registry) v.elementAt(l);
						registry2 = (Registry) v.elementAt(m);
						
						
						if(registry1.getTipoPet().compareTo(registry2.getTipoPet()) > 0){
							temp = (Registry)v.elementAt(m);							
							v.setElementAt(v.elementAt(l),m);
							v.setElementAt(temp,l);
						}
						
					}
				}
				
				saveVector();
			}else{
				
				Registry registry1 = new Registry();
				Registry registry2 = new Registry();
				Registry temp = new Registry();
				int l,m;
				
				for(l=0;l<v.size()-1;l++){
					for(m=l+1;m<v.size();m++){
						registry1 = (Registry) v.elementAt(l);
						registry2 = (Registry) v.elementAt(m);
						
						
						if(registry1.getNomeDono().compareTo(registry2.getNomeDono()) > 0){
							temp = (Registry)v.elementAt(m);
							
							v.setElementAt(v.elementAt(l),m);
							v.setElementAt(temp,l);
						}
						
					}
				}
				
				saveVector();
			}
			
			newFrame.setVisible(false);
		}
			
	}
		
	public void saveVector(){
		t = new Thread(this, "Save Vector Thread");
		t.start();
		
	}
		
	public void valueChanged(ListSelectionEvent lse){
		
		
	}
	
}