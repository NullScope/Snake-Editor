import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class Main extends JFrame{
	private static ImageIcon grassICO = new ImageIcon("grass3.png");
	private static ImageIcon vinesICO = new ImageIcon("vines.png");
	private static ImageIcon macaICO = new ImageIcon("maca.png");
	private static ImageIcon limaoICO = new ImageIcon("limao.png");
	private static ImageIcon melanICO = new ImageIcon("melancia.png");

	private static ImageIcon headICO =  new ImageIcon("headLEFT.png");
	private static ImageIcon tailICO =  new ImageIcon("tailLEFT.png");
	
	private static ImageIcon enemyHeadICO =  new ImageIcon("enemyHEADLEFT.png");
	private static ImageIcon enemyTailICO =  new ImageIcon("enemyTAILLEFT.png");
	
	private static ImageIcon bodyLEFTICO =  new ImageIcon("bodyLEFT.png");
	private static ImageIcon bodyRIGHTICO =  new ImageIcon("bodyRIGHT.png");
	private static ImageIcon bodyDOWNICO =  new ImageIcon("bodyDOWN.png");
	private static ImageIcon bodyUPICO =  new ImageIcon("bodyUP.png");
	
	private static ImageIcon enemyLEFTICO =  new ImageIcon("enemyLEFT.png");
	private static ImageIcon enemyRIGHTICO =  new ImageIcon("enemyRIGHT.png");
	private static ImageIcon enemyDOWNICO =  new ImageIcon("enemyDOWN.png");
	private static ImageIcon enemyUPICO =  new ImageIcon("enemyUP.png");
	
	private static Direction up = new Direction("CIMA", '^');
	private static Direction down = new Direction("BAIXO", '/');
	private static Direction left = new Direction("ESQUERDA", '<');
	private static Direction right = new Direction("DIREITA", '>');
	
	private static Direction dir_chose = left;
	private static Direction enemy_chose = left;
	
	private static Vector<Body> bodies = new Vector<Body>();
	private static Vector<Body> enemyBodies = new Vector<Body>();
	
	private static JComboBox<Direction> playerDir = new JComboBox<Direction>();
	private static JComboBox<Direction> enemyDir = new JComboBox<Direction>();
	
	private static JPanel panel = new JPanel();
	private static JPanel panel2 = new JPanel();
	private static JPanel panel3 = new JPanel();
	private static JPanel panel4 = new JPanel();
	
	private static JLabel label1 = new JLabel("DIREÇÃO DO JOGADOR");
	private static JLabel label2 = new JLabel("POINTS");
	private static JLabel label3 = new JLabel(" ");
	private static JLabel label4 = new JLabel("DIREÇÃO DO INIMIGO");
	
	private static JTextArea areaPoints = new JTextArea();
	private static JButton grassBTN = new JButton("Relva                         ", grassICO);
	private static JButton vinesBTN = new JButton("Paredes                    ",vinesICO);
	private static JButton macaBTN = new JButton("Maçã                          ", macaICO);
	private static JButton limaoBTN = new JButton("Limão                         ",limaoICO);
	private static JButton melanBTN = new JButton("Melancia                   ",melanICO);
	private static JButton bodyBTN = new JButton("Corpo do Jogador   ",bodyLEFTICO);
	private static JButton headBTN = new JButton("Cabeça do Jogador",headICO);
	private static JButton tailBTN = new JButton("Cauda do Jogador  ",tailICO);
	private static JButton enemyBodyBTN = new JButton("Corpo do Inimigo     ", enemyLEFTICO);
	private static JButton enemyHeadBTN = new JButton("Cabeça do Inimigo  ", enemyHeadICO);
	private static JButton enemyTailBTN = new JButton("Cauda do Inimigo    ", enemyTailICO);
	private static JButton reset = new JButton("Reset");
	private static JButton save = new JButton("Guardar");
	private static JButton load = new JButton("Carregar");
	
	private static final int largura = 40;
	private static final int altura = 40;
	private static final int escala = 15;
	private static int chosen = 0;
	private static int points = 0;
	private static int drag = 0;
	private static int headPlaced = 0;
	private static int enemyHeadPlaced = 0;
	private static int tailPlaced = 0;
	private static int enemyTailPlaced = 0;
	private static int counter = 0;
	private static int j = -1;
	private static int k = -1;
	
	private static Object[] options = {"Sim","Não"};
	
	private static Timer clock;
	
	private static char matrix[][] = new char[largura][altura];
	
	public Main(){
		super("Snake Editor v0.99");
		setSize(829,664);
		setVisible(true);
		panel.setBounds(0, 0, largura*escala, altura*escala);
		panel.setSize(largura, altura);
		resetMatrix();
		setupGraphics();
		setupActions();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void setupGraphics() {
		JPanel sub = new JPanel();
		JPanel sub2 = new JPanel();
		JPanel sub3 = new JPanel();

		setLayout(new BorderLayout());
		panel.setLayout(new GridLayout(altura, largura));
		panel3.setLayout(new GridLayout(23,0));
		panel4.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		playerDir.addItem(right);
		playerDir.addItem(left);
		playerDir.addItem(down);
		playerDir.addItem(up);
		
		enemyDir.addItem(right);
		enemyDir.addItem(left);
		enemyDir.addItem(down);
		enemyDir.addItem(up);
		
		
		panel2.setBackground(Color.GRAY);
		panel4.setBackground(Color.GRAY);
		sub.setBackground(Color.GRAY);
		sub2.setBackground(Color.GRAY);
		sub3.setBackground(Color.GRAY);
		
		label3.setForeground(Color.YELLOW);
		
		add(panel, BorderLayout.CENTER);
		add(panel2, BorderLayout.WEST);
		add(panel4, BorderLayout.SOUTH);
		panel2.add(panel3);
		
		panel3.add(grassBTN);
		panel3.add(vinesBTN);
		panel3.add(bodyBTN);
		panel3.add(headBTN);
		panel3.add(tailBTN);
		panel3.add(enemyBodyBTN);
		panel3.add(enemyHeadBTN);
		panel3.add(enemyTailBTN);
		panel3.add(macaBTN);
		panel3.add(limaoBTN);
		panel3.add(melanBTN);
		panel3.add(label1);
		panel3.add(playerDir);
		panel3.add(label4);
		panel3.add(enemyDir);
		panel3.add(label2);
		panel3.add(areaPoints);
		
		panel3.add(sub);
		panel3.add(sub2);
		panel3.add(sub3);
		
		panel3.add(load);
		panel3.add(save);
		panel3.add(reset);
		panel4.add(label3);
		
	}
	
	private void setupActions() {
		clock=new javax.swing.Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(counter > 0){
					counter--;
				}else{
					label3.setText(" ");
				}
				//update JTextField
				clock.start();
		    }
		} );
		clock.start();
		panel.repaint();
		panel.revalidate();
		
		load.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				
				int option = fc.showOpenDialog(Main.this);
				if(option == JFileChooser.APPROVE_OPTION){
					File level = fc.getSelectedFile();
					loadMatrix(level);
				}
			}

			
		});
		
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane resetWarning = new JOptionPane();
				@SuppressWarnings("static-access")
				int option = resetWarning.showOptionDialog(null, "Tem a certeza que quer apagar tudo?", null, JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
				
				if(option == 0){
					resetMatrix();
				}
			}
		});
		
		save.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				if(!areaPoints.getText().isEmpty()){
					if(playerDir.getSelectedIndex() > -1){
						if(bodies.size() > 0){
							if(tailPlaced == 1){
							String temp = "";
							
							for(int y = 0; y < 40; y++){
								for(int x = 0; x < 40; x++){
									temp += ""+ matrix[x][y];
								}
								temp += "\r";
								temp += "\n";
							}
							
							JFileChooser fc = new JFileChooser();
							
							int option = fc.showSaveDialog(Main.this);
							
							dir_chose = (Direction) playerDir.getSelectedItem();
							if(enemyHeadPlaced == 1 && enemyTailPlaced == 1){
								enemy_chose = (Direction) enemyDir.getSelectedItem();
							}
							if(option == JFileChooser.APPROVE_OPTION){
								FileWriter fstream;
								try{
									points = Integer.parseInt(areaPoints.getText());
									try {
										fstream = new FileWriter(fc.getSelectedFile());
										BufferedWriter out = new BufferedWriter(fstream);
										out.write(temp);
										out.write(points+"\r"+"\n"+dir_chose.getChar_dir());
										if(enemyHeadPlaced == 1 && enemyTailPlaced == 1){
											out.write("\r"+"\n"+enemy_chose.getChar_dir());
										}
										out.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}catch(Exception e){
									label3.setText("Por favor introduza um valor valido para os pontos");
									counter = 3;
								}
							}
							}else{
								label3.setText("Por favor introduza a cauda da cobra");
								counter = 3;
							}
						}else{
							label3.setText("Por favor complete a cobra");
							counter = 3;
						}
					}else{
						label3.setText("Por favor selecione a direção em que a cobra irá começar");
						counter = 3;
					}
				}else{
					label3.setText("Por favor preencha os pontos necessários para acabar o nivel");
					counter = 3;
				}
			}
		});
		
		grassBTN.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				grassBTN.setEnabled(false);
				vinesBTN.setEnabled(true);
				bodyBTN.setEnabled(true);
				if(headPlaced==0){
					headBTN.setEnabled(true);
				}
				if(tailPlaced == 0){
					tailBTN.setEnabled(true);
				}
				enemyBodyBTN.setEnabled(true);
				if(enemyHeadPlaced == 0){
					enemyHeadBTN.setEnabled(true);
				}
				if(enemyTailPlaced == 0){
					enemyTailBTN.setEnabled(true);
				}
				macaBTN.setEnabled(true);
				limaoBTN.setEnabled(true);
				melanBTN.setEnabled(true);
				
				chosen = 1;
			}
		});
		
		vinesBTN.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				grassBTN.setEnabled(true);
				vinesBTN.setEnabled(false);
				bodyBTN.setEnabled(true);
				if(headPlaced==0){
					headBTN.setEnabled(true);
				}
				if(tailPlaced == 0){
					tailBTN.setEnabled(true);
				}
				enemyBodyBTN.setEnabled(true);
				if(enemyHeadPlaced == 0){
					enemyHeadBTN.setEnabled(true);
				}
				if(enemyTailPlaced == 0){
					enemyTailBTN.setEnabled(true);
				}
				macaBTN.setEnabled(true);
				limaoBTN.setEnabled(true);
				melanBTN.setEnabled(true);
				
				chosen = 2;
			}
		});
		
		bodyBTN.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				grassBTN.setEnabled(true);
				vinesBTN.setEnabled(true);
				bodyBTN.setEnabled(false);
				if(headPlaced==0){
					headBTN.setEnabled(true);
				}
				if(tailPlaced == 0){
					tailBTN.setEnabled(true);
				}
				enemyBodyBTN.setEnabled(true);
				if(enemyHeadPlaced == 0){
					enemyHeadBTN.setEnabled(true);
				}
				if(enemyTailPlaced == 0){
					enemyTailBTN.setEnabled(true);
				}
				macaBTN.setEnabled(true);
				limaoBTN.setEnabled(true);
				melanBTN.setEnabled(true);
				chosen = 3;
			}
		});
		
		headBTN.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				grassBTN.setEnabled(true);
				vinesBTN.setEnabled(true);
				bodyBTN.setEnabled(true);
				headBTN.setEnabled(false);
				if(tailPlaced == 0){
					tailBTN.setEnabled(true);
				}
				enemyBodyBTN.setEnabled(true);
				if(enemyHeadPlaced == 0){
					enemyHeadBTN.setEnabled(true);
				}
				if(enemyTailPlaced == 0){
					enemyTailBTN.setEnabled(true);
				}
				macaBTN.setEnabled(true);
				limaoBTN.setEnabled(true);
				melanBTN.setEnabled(true);
				chosen = 4;
			}
		});
		
		tailBTN.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				grassBTN.setEnabled(true);
				vinesBTN.setEnabled(true);
				bodyBTN.setEnabled(true);
				if(headPlaced==0){
					headBTN.setEnabled(true);
				}
				tailBTN.setEnabled(false);
				enemyBodyBTN.setEnabled(true);
				if(enemyHeadPlaced == 0){
					enemyHeadBTN.setEnabled(true);
				}
				if(enemyTailPlaced == 0){
					enemyTailBTN.setEnabled(true);
				}
				macaBTN.setEnabled(true);
				limaoBTN.setEnabled(true);
				melanBTN.setEnabled(true);
				chosen = 5;
			}
		});
		
		
		macaBTN.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				grassBTN.setEnabled(true);
				vinesBTN.setEnabled(true);
				bodyBTN.setEnabled(true);
				if(headPlaced==0){
					headBTN.setEnabled(true);
				}
				if(tailPlaced == 0){
					tailBTN.setEnabled(true);
				}
				enemyBodyBTN.setEnabled(true);
				if(enemyHeadPlaced == 0){
					enemyHeadBTN.setEnabled(true);
				}
				if(enemyTailPlaced == 0){
					enemyTailBTN.setEnabled(true);
				}
				macaBTN.setEnabled(false);
				limaoBTN.setEnabled(true);
				melanBTN.setEnabled(true);
				
				chosen = 6;
			}
		});
		
		limaoBTN.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				grassBTN.setEnabled(true);
				vinesBTN.setEnabled(true);
				bodyBTN.setEnabled(true);
				if(headPlaced==0){
					headBTN.setEnabled(true);
				}
				if(tailPlaced == 0){
					tailBTN.setEnabled(true);
				}
				enemyBodyBTN.setEnabled(true);
				if(enemyHeadPlaced == 0){
					enemyHeadBTN.setEnabled(true);
				}
				if(enemyTailPlaced == 0){
					enemyTailBTN.setEnabled(true);
				}
				macaBTN.setEnabled(true);
				limaoBTN.setEnabled(false);
				melanBTN.setEnabled(true);
				chosen = 7;
			}
		});
		
		melanBTN.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				grassBTN.setEnabled(true);
				vinesBTN.setEnabled(true);
				bodyBTN.setEnabled(true);
				if(headPlaced==0){
					headBTN.setEnabled(true);
				}
				if(tailPlaced == 0){
					tailBTN.setEnabled(true);
				}
				enemyBodyBTN.setEnabled(true);
				if(enemyHeadPlaced == 0){
					enemyHeadBTN.setEnabled(true);
				}
				if(enemyTailPlaced == 0){
					enemyTailBTN.setEnabled(true);
				}
				macaBTN.setEnabled(true);
				limaoBTN.setEnabled(true);
				melanBTN.setEnabled(false);
				chosen = 8;
			}
		});
		
		enemyBodyBTN.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				grassBTN.setEnabled(true);
				vinesBTN.setEnabled(true);
				bodyBTN.setEnabled(true);
				if(headPlaced==0){
					headBTN.setEnabled(true);
				}
				if(tailPlaced == 0){
					tailBTN.setEnabled(true);
				}
				enemyBodyBTN.setEnabled(false);
				if(enemyHeadPlaced == 0){
					enemyHeadBTN.setEnabled(true);
				}
				if(enemyTailPlaced == 0){
					enemyTailBTN.setEnabled(true);
				}
				macaBTN.setEnabled(true);
				limaoBTN.setEnabled(true);
				melanBTN.setEnabled(true);
				
				chosen = 9;
			}
		});
		
		enemyHeadBTN.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				grassBTN.setEnabled(true);
				vinesBTN.setEnabled(true);
				bodyBTN.setEnabled(true);
				if(headPlaced==0){
					headBTN.setEnabled(true);
				}
				if(tailPlaced == 0){
					tailBTN.setEnabled(true);
				}
				enemyBodyBTN.setEnabled(true);
				enemyHeadBTN.setEnabled(false);
				if(enemyTailPlaced == 0){
					enemyTailBTN.setEnabled(true);
				}
				macaBTN.setEnabled(true);
				limaoBTN.setEnabled(true);
				melanBTN.setEnabled(true);
				
				chosen = 10;
			}
		});
		
		enemyTailBTN.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				grassBTN.setEnabled(false);
				vinesBTN.setEnabled(true);
				bodyBTN.setEnabled(true);
				if(headPlaced==0){
					headBTN.setEnabled(true);
				}
				if(tailPlaced == 0){
					tailBTN.setEnabled(true);
				}
				enemyBodyBTN.setEnabled(true);
				if(enemyHeadPlaced == 0){
					enemyHeadBTN.setEnabled(true);
				}
				enemyTailBTN.setEnabled(false);
				macaBTN.setEnabled(true);
				limaoBTN.setEnabled(true);
				melanBTN.setEnabled(true);
				
				chosen = 11;
			}
		});
	}
	private void loadMatrix(File level) {
		String ultimoSTR = "";
		String penultimoSTR = "";
		String antepenultimoSTR = "";
		int y = 0;
		try {
			panel.removeAll();
	        BufferedReader temp = new BufferedReader(new FileReader(level));
	        String str;
	        while((str = temp.readLine()) != null){
	        	antepenultimoSTR = penultimoSTR;
	        	penultimoSTR = ultimoSTR;
	        	ultimoSTR = str;
	        	if(y < 40){
		        	for(int x = 0; x<largura; x++){	
		    			matrix[x][y] = str.charAt(x);
		    			switch(matrix[x][y]){
			    			case('K'):{
			    				addButtons(x, y, enemyHeadICO);
			    				enemyHeadPlaced = 1;
			    				break;
			    			}
			    			case('B'):{
			    				enemyBodies.add(new Body(x, y));
			    				k += 1;
			    				addButtons(x, y, enemyLEFTICO);
			    				break;
			    			}
			    			case('D'):{
			    				enemyBodies.add(new Body(x, y));
			    				k += 1;
			    				addButtons(x, y, enemyTailICO);
			    				enemyTailPlaced = 1;
			    				break;
			    			}
			    			case('H'):{
			    				addButtons(x, y, headICO);
			    				headPlaced = 1;
			    				break;
			    			}
			    			case('S'):{
			    				bodies.add(new Body(x, y));
			    				j += 1;
			    				addButtons(x, y, bodyLEFTICO);
			    				break;
			    			}
			    			case('T'):{
			    				bodies.add(new Body(x, y));
			    				j += 1;
			    				addButtons(x, y, tailICO);
			    				tailPlaced = 1;
			    				break;
			    			}
			    			case('w'):{
			    				addButtons(x, y, vinesICO);
			    				break;
			    			}
			    			case('_'):{
			    				addButtons(x, y, grassICO);
			    				break;
			    			}
			    			case('F'):{
			    				addButtons(x, y, macaICO);
			    				break;
			    			}
			    			case('L'):{
			    				addButtons(x, y, limaoICO);
			    				break;
			    			}
			    			case('M'):{
			    				addButtons(x, y, melanICO);
			    				break;
			    			}
		    			}
		        	}
		        	y+=1;
	        	}
	        }
	        if(enemyHeadPlaced == 0){
       		 points = Integer.parseInt(penultimoSTR);
		        switch(ultimoSTR){
		        	case("^"):{
		        		refillPlayerDir(1);
		        		break;
		        	}
		        	case("/"):{
		        		refillPlayerDir(2);
		        		break;
		        	}
		        	case(">"):{
		        		refillPlayerDir(3);
		        		break;
		        	}
		        	case("<"):{
		        		refillPlayerDir(4);
		        		break;
		        	}
		        }
       	}else{
       		 points = Integer.parseInt(antepenultimoSTR);
       		switch(penultimoSTR){
		        	case("^"):{
		        		refillPlayerDir(1);
		        		break;
		        	}
		        	case("/"):{
		        		refillPlayerDir(2);
		        		break;
		        	}
		        	case(">"):{
		        		refillPlayerDir(3);
		        		break;
		        	}
		        	case("<"):{
		        		refillPlayerDir(4);
		        		break;
		        	}
       		}
       		
       		switch(ultimoSTR){
		        	case("^"):{
		        		refillEnemyDir(1);
		        		break;
		        	}
		        	case("/"):{
		        		refillEnemyDir(2);
		        		break;
		        	}
		        	case(">"):{
		        		refillEnemyDir(3);
		        		break;
		        	}
		        	case("<"):{
		        		refillEnemyDir(4);
		        		break;
		        	}
       		}
       	}
	        areaPoints.setText(""+points);
	    temp.close();
		}catch(Exception e){
			
		}
		panel.repaint();
		panel.revalidate();
	}
	
	private void resetMatrix(){
		panel.removeAll();
		for(int y = 0; y<40; y++){
			for(int x = 0; x<40; x++){
				matrix[x][y] = '_';
				addButtons(x, y, grassICO);
			}
		}
		panel.repaint();
		panel.revalidate();
	}

	private void addButtons(int x, int y, ImageIcon icon) {
		final JButton temp = new JButton();
		final int temp_x = x;
		final int temp_y = y;
		
		temp.setSize(escala, escala);
		temp.setIcon(icon);
		
		panel.add(temp);
		
		temp.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				drag = 0;
				clock.start();
			}
			
			public void mousePressed(MouseEvent e) {
				drag = 1;
				updateMatrix(temp_x, temp_y, temp);
			}
			
			public void mouseExited(MouseEvent e) {
				
			}
			
			public void mouseEntered(MouseEvent e) {
				if(drag == 1){
					updateMatrix(temp_x, temp_y, temp);
				}
			}

			public void mouseClicked(MouseEvent e) {
				
			}
		});
	}
	
	private void updateMatrix(int temp_x, int temp_y, JButton temp) {
		if(bodies.size() > 0 && chosen != 3 && matrix[temp_x][temp_y]== 'S' && (temp_x != bodies.lastElement().getX() || temp_y != bodies.lastElement().getY())){
			label3.setText("Por favor elimine as ultimas peça(s) introduzidas antes de remover esta");
			counter = 3;
			return;
		}
		
		if(enemyBodies.size() > 0 && chosen != 9 && matrix[temp_x][temp_y]== 'B' && (temp_x != enemyBodies.lastElement().getX() || temp_y != enemyBodies.lastElement().getY())){
			label3.setText("Por favor elimine as ultimas peça(s) introduzidas antes de remover esta");
			counter = 3;
			return;
		}
		
		if(matrix[temp_x][temp_y] == 'H' && chosen != 3 && chosen != 5 && bodies.size() == 0){
			headPlaced = 0;
			headBTN.setEnabled(true);
		}
		
		if(matrix[temp_x][temp_y] == 'K' && chosen != 9 && chosen != 11 && enemyBodies.size() == 0){
			enemyHeadPlaced = 0;
			enemyHeadBTN.setEnabled(true);
		}
		
		if(matrix[temp_x][temp_y] == 'T' && chosen != 3 && bodies.size() >= 1){
			tailPlaced = 0;
			tailBTN.setEnabled(true);
		}
		
		if(matrix[temp_x][temp_y] == 'D' && chosen != 9 && enemyBodies.size() >= 1){
			enemyTailPlaced = 0;
			enemyTailBTN.setEnabled(true);
		}
		
		if(matrix[temp_x][temp_y] == 'H' && bodies.size()>0){
			label3.setText("Por favor elimine as peças do corpo antes de remover a cabeça");
			counter = 3;
			return;
		}
		
		if(matrix[temp_x][temp_y] == 'K' && enemyBodies.size()>0){
			label3.setText("Por favor elimine as peças do corpo antes de remover a cabeça");
			counter = 3;
			return;
		}
		
		if(chosen != 3 && (matrix[temp_x][temp_y] == 'S' || matrix[temp_x][temp_y] == 'T') && temp_x == bodies.lastElement().getX() && temp_y == bodies.lastElement().getY()){
			bodies.remove(j);
			j -= 1;
		}
		
		if(chosen != 9 && (matrix[temp_x][temp_y] == 'B' || matrix[temp_x][temp_y] == 'D') && temp_x == enemyBodies.lastElement().getX() && temp_y == enemyBodies.lastElement().getY()){
			enemyBodies.remove(k);
			k -= 1;
		}
		
		switch(chosen){
		
			case(1):{
				matrix[temp_x][temp_y] = '_';
				temp.setIcon(grassICO);
				break;
			}
			case(2):{
				matrix[temp_x][temp_y] = 'w';
				temp.setIcon(vinesICO);
				break;
			}
			case(3):{
				if(headPlaced == 1){
					if(tailPlaced == 0){
						if(matrix[temp_x][temp_y] != 'H'){
							if(matrix[temp_x][temp_y] != 'S'){
								if(bodies.size() == 0){
									if((matrix[temp_x-1][temp_y] == 'H' || matrix[temp_x+1][temp_y] == 'H' || matrix[temp_x][temp_y-1] == 'H' || matrix[temp_x][temp_y+1] == 'H')){
										matrix[temp_x][temp_y] = 'S';
										
										bodies.add(new Body(temp_x, temp_y));
										j += 1;
										
										if(matrix[bodies.firstElement().getX()-1][bodies.firstElement().getY()] == 'H'){
											refillPlayerDir(1);
											temp.setIcon(bodyLEFTICO);
										}
										if(matrix[bodies.firstElement().getX()+1][bodies.firstElement().getY()] == 'H'){
											refillPlayerDir(2);
											temp.setIcon(bodyRIGHTICO);
										}
										if(matrix[bodies.firstElement().getX()][bodies.firstElement().getY()-1] == 'H'){
											refillPlayerDir(3);
											temp.setIcon(bodyUPICO);
										}
										if(matrix[bodies.firstElement().getX()][bodies.firstElement().getY()+1] == 'H'){
											refillPlayerDir(4);
											temp.setIcon(bodyDOWNICO);
										}
									}else{
										label3.setText("Por favor introduza a peça do corpo perto da cabeça");
										counter = 3;
									}
								}else{
									if((temp_x-1 == bodies.lastElement().getX() && temp_y == bodies.lastElement().getY()) || (temp_x+1 == bodies.lastElement().getX() && temp_y == bodies.lastElement().getY()) || (temp_x == bodies.lastElement().getX() && temp_y-1 == bodies.lastElement().getY())|| (temp_x == bodies.lastElement().getX() && temp_y+1 == bodies.lastElement().getY())){
										matrix[temp_x][temp_y] = 'S';
										bodies.add(new Body(temp_x, temp_y));
										
										j += 1;
										
										if(bodies.get(j).getX() - bodies.get(j-1).getX() == 1){
											temp.setIcon(bodyLEFTICO);
										}
										if(bodies.get(j).getX() - bodies.get(j-1).getX() == -1){
											temp.setIcon(bodyRIGHTICO);
										}
										if(bodies.get(j).getY() - bodies.get(j-1).getY() == 1){
											temp.setIcon(bodyUPICO);
										}
										if(bodies.get(j).getY() - bodies.get(j-1).getY() == -1){
											temp.setIcon(bodyDOWNICO);
										}
										
									}else{
										label3.setText("Por favor introduza a peça do corpo ao pe da ultima intruduzida");
										counter = 3;
									}
								}
							}else{
								label3.setText("Não pode introduzir uma peça do corpo noutra peça");
								counter = 3;
							}
						}else{
							label3.setText("Não pode introduzir uma peça do corpo na cabeça");
							counter = 3;
						}
					}else{
						label3.setText("Não pode introduzir uma peça do corpo depois de introduzir a cauda");
						counter = 3;
					}
				}else{
					label3.setText("Por favor introduza a cabeça antes de introduzir o corpo");
					counter = 3;
				}
				
				break;
			}
			case(4):{
				if(headPlaced == 0){
					matrix[temp_x][temp_y] = 'H';
					temp.setIcon(headICO);
					headPlaced = 1;
					headBTN.setEnabled(false);
				}else{
					label3.setText("A cabeça da cobra já foi introduzida, por favor remova a anterior");
					counter = 3;
					}
				break;
			}
			case(5):{
				if(headPlaced == 1){
					if(tailPlaced == 0){
						if(matrix[temp_x][temp_y] != 'H'){
							if(bodies.size() == 0){
								if((matrix[temp_x-1][temp_y] == 'H' || matrix[temp_x+1][temp_y] == 'H' || matrix[temp_x][temp_y-1] == 'H' || matrix[temp_x][temp_y+1] == 'H')){
									matrix[temp_x][temp_y] = 'T';
									temp.setIcon(tailICO);
									bodies.add(new Body(temp_x, temp_y));
									j += 1;
									tailPlaced = 1;
									tailBTN.setEnabled(false);
								}else{
									label3.setText("Por favor introduza a cauda perto da cabeça");
									counter = 3;
								}
							}else{
								if((temp_x-1 == bodies.lastElement().getX() && temp_y == bodies.lastElement().getY()) || (temp_x+1 == bodies.lastElement().getX() && temp_y == bodies.lastElement().getY()) || (temp_x == bodies.lastElement().getX() && temp_y-1 == bodies.lastElement().getY())|| (temp_x == bodies.lastElement().getX() && temp_y+1 == bodies.lastElement().getY())){
									matrix[temp_x][temp_y] = 'T';
									temp.setIcon(tailICO);
									bodies.add(new Body(temp_x, temp_y));
									j += 1;
									tailPlaced = 1;
								}else{
									label3.setText("Por favor introduza a cauda ao pe da ultima peça do corpo intruduzida");
									counter = 3;
								}
							}
						}else{
							label3.setText("Não pode introduzir a cauda em cima da cabeça");
							counter = 3;
						}
					}else{
						label3.setText("A cauda da cobra já foi introduzida, por favor remova a anterior");
						counter = 3;
					}
				}else{
					label3.setText("Por favor introduza a cabeça antes de introduzir a cauda");
					counter = 3;
				}
				break;
			}
			case(6):{
				matrix[temp_x][temp_y] = 'F';
				temp.setIcon(macaICO);
				break;
			}
			case(7):{
				matrix[temp_x][temp_y] = 'L';
				temp.setIcon(limaoICO);
				break;
			}
			case(8):{
				matrix[temp_x][temp_y] = 'M';
				temp.setIcon(melanICO);
				break;
			}
			case(9):{
				if(enemyHeadPlaced == 1){
					if(enemyTailPlaced == 0){
						if(matrix[temp_x][temp_y] != 'K'){
							if(matrix[temp_x][temp_y] != 'B'){
								if(enemyBodies.size() == 0){
									if((matrix[temp_x-1][temp_y] == 'K' || matrix[temp_x+1][temp_y] == 'K' || matrix[temp_x][temp_y-1] == 'K' || matrix[temp_x][temp_y+1] == 'K')){
										matrix[temp_x][temp_y] = 'B';
										
										enemyBodies.add(new Body(temp_x, temp_y));
										k += 1;
										
										if(matrix[enemyBodies.firstElement().getX()-1][enemyBodies.firstElement().getY()] == 'K'){
											refillEnemyDir(1);
											temp.setIcon(enemyLEFTICO);
										}
										if(matrix[enemyBodies.firstElement().getX()+1][enemyBodies.firstElement().getY()] == 'K'){
											refillEnemyDir(2);
											temp.setIcon(enemyRIGHTICO);
										}
										if(matrix[enemyBodies.firstElement().getX()][enemyBodies.firstElement().getY()-1] == 'K'){
											refillEnemyDir(3);
											temp.setIcon(enemyUPICO);
										}
										if(matrix[enemyBodies.firstElement().getX()][enemyBodies.firstElement().getY()+1] == 'K'){
											refillEnemyDir(4);
											temp.setIcon(enemyDOWNICO);
										}
									}else{
										label3.setText("Por favor introduza a peça do corpo perto da cabeça");
										counter = 3;
									}
								}else{
									if((temp_x-1 == enemyBodies.lastElement().getX() && temp_y == enemyBodies.lastElement().getY()) || (temp_x+1 == enemyBodies.lastElement().getX() && temp_y == enemyBodies.lastElement().getY()) || (temp_x == enemyBodies.lastElement().getX() && temp_y-1 == enemyBodies.lastElement().getY())|| (temp_x == enemyBodies.lastElement().getX() && temp_y+1 == enemyBodies.lastElement().getY())){
										matrix[temp_x][temp_y] = 'B';
										enemyBodies.add(new Body(temp_x, temp_y));
										k += 1;
										
										if(enemyBodies.get(k).getX() - enemyBodies.get(k-1).getX() == 1){
											temp.setIcon(enemyLEFTICO);
										}
										if(enemyBodies.get(k).getX() - enemyBodies.get(k-1).getX() == -1){
											temp.setIcon(enemyRIGHTICO);
										}
										if(enemyBodies.get(k).getY() - enemyBodies.get(k-1).getY() == 1){
											temp.setIcon(enemyUPICO);
										}
										if(enemyBodies.get(k).getY() - enemyBodies.get(k-1).getY() == -1){
											temp.setIcon(enemyDOWNICO);
										}
										
									}else{
										label3.setText("Por favor introduza a peça do corpo ao pe da ultima intruduzida");
										counter = 3;
									}
								}
							}else{
								label3.setText("Não pode introduzir uma peça do corpo noutra peça");
								counter = 3;
							}
						}else{
							label3.setText("Não pode introduzir uma peça do corpo na cabeça");
							counter = 3;
						}
					}else{
						label3.setText("Não pode introduzir uma peça do corpo depois de introduzir a cauda");
						counter = 3;
					}
				}else{
					label3.setText("Por favor introduza a cabeça antes de introduzir o corpo");
					counter = 3;
				}
				
				break;
			}
			case(10):{
				if(enemyHeadPlaced == 0){
					matrix[temp_x][temp_y] = 'K';
					temp.setIcon(enemyHeadICO);
					enemyHeadPlaced = 1;
					enemyHeadBTN.setEnabled(false);
				}else{
					label3.setText("A cabeça da cobra já foi introduzida, por favor remova a anterior");
					counter = 3;
					}
				break;
			}
			case(11):{
				if(enemyHeadPlaced == 1){
					if(enemyTailPlaced == 0){
						if(matrix[temp_x][temp_y] != 'K'){
							if(enemyBodies.size() == 0){
								if((matrix[temp_x-1][temp_y] == 'K' || matrix[temp_x+1][temp_y] == 'K' || matrix[temp_x][temp_y-1] == 'K' || matrix[temp_x][temp_y+1] == 'K')){
									matrix[temp_x][temp_y] = 'D';
									temp.setIcon(enemyTailICO);
									enemyBodies.add(new Body(temp_x, temp_y));
									k += 1;
									enemyTailPlaced = 1;
									enemyTailBTN.setEnabled(false);
								}else{
									label3.setText("Por favor introduza a cauda perto da cabeça");
									counter = 3;
								}
							}else{
								if((temp_x-1 == enemyBodies.lastElement().getX() && temp_y == enemyBodies.lastElement().getY()) || (temp_x+1 == enemyBodies.lastElement().getX() && temp_y == enemyBodies.lastElement().getY()) || (temp_x == enemyBodies.lastElement().getX() && temp_y-1 == enemyBodies.lastElement().getY())|| (temp_x == enemyBodies.lastElement().getX() && temp_y+1 == enemyBodies.lastElement().getY())){
									matrix[temp_x][temp_y] = 'D';
									temp.setIcon(enemyTailICO);
									enemyBodies.add(new Body(temp_x, temp_y));
									k += 1;
									enemyTailPlaced = 1;
								}else{
									label3.setText("Por favor introduza a cauda ao pe da ultima peça do corpo intruduzida");
									counter = 3;
								}
							}
						}else{
							label3.setText("Não pode introduzir a cauda em cima da cabeça");
							counter = 3;
						}
					}else{
						label3.setText("A cauda da cobra já foi introduzida, por favor remova a anterior");
						counter = 3;
					}
				}else{
					label3.setText("Por favor introduza a cabeça antes de introduzir a cauda");
					counter = 3;
				}
				break;
			}
		}
	}
	
	private void refillPlayerDir(int i) {
		playerDir.removeAllItems();
		switch(i){
			case(1):{
				playerDir.addItem(left);
				playerDir.addItem(down);
				playerDir.addItem(up);
			break;
			}
			case(2):{
				playerDir.addItem(right);
				playerDir.addItem(down);
				playerDir.addItem(up);
			break;
			}
			case(3):{
				playerDir.addItem(left);
				playerDir.addItem(right);
				playerDir.addItem(up);
			break;
			}
			case(4):{
				playerDir.addItem(left);
				playerDir.addItem(right);
				playerDir.addItem(down);
			break;
			}
		}
		panel3.repaint();
		panel3.revalidate();
	}

	private void refillEnemyDir(int i) {
		enemyDir.removeAllItems();
		switch(i){
			case(1):{
				enemyDir.addItem(left);
				enemyDir.addItem(down);
				enemyDir.addItem(up);
			break;
			}
			case(2):{
				enemyDir.addItem(right);
				enemyDir.addItem(down);
				enemyDir.addItem(up);
			break;
			}
			case(3):{
				enemyDir.addItem(left);
				enemyDir.addItem(right);
				enemyDir.addItem(up);
			break;
			}
			case(4):{
				enemyDir.addItem(left);
				enemyDir.addItem(right);
				enemyDir.addItem(down);
			break;
			}
		}
		panel3.repaint();
		panel3.revalidate();
	}
	public BufferedImage loadImage(String nome) {
		URL url= null;
		try {
			url = getClass().getClassLoader().getResource(nome);
			return ImageIO.read(url);
		} catch (Exception e) {
			System.out.println("Não foi possivel carregar a imagem " + nome +" de "+url);
			System.out.println("O Erro foi : "+e.getClass().getName()+" "+e.getMessage());
			System.exit(0);
			return null;
		}
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Main screen = new Main();
	}

}