import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import com.sun.jna.NativeLibrary;

import model.Liste;
import uk.co.caprica.vlcj.binding.RuntimeUtil;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class View {

	protected Shell shell;
	private Text txtEntrerLeChemin;
	private Button btnValider;
	private Composite composite_1;
	private Button btnChance;
	private Button btnHistorique;
	private Button btnNettoyer;
	private Composite composite_2;
	private Button btnMp4;
	private Button btnAvi;
	private Button btnMkv;
	private Button btnPdf;
	private Button btnExe;
	private Button btnMp3;
	private Button btnCbr;
	private Button btnFlac;
	private Button btnJpeg;
	private Liste l;
	private Button[] btn = new Button[] {btnAvi, btnCbr, btnExe, btnFlac, btnJpeg, btnMkv, btnMp3, btnMp4, btnPdf};
	private boolean[] state = new boolean[] {false, false, false, false, false, false, false, false, false};
	private String[] ext = new String[] {"avi", "cbr", "exe", "flac", "jpg", "mkv", "mp3", "mp4", "pdf"};

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			View window = new View();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 393);
		shell.setText("Choix fichier");
		shell.setLayout(new FormLayout());
		
		Composite composite = new Composite(shell, SWT.BORDER);
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0, 20);
		fd_composite.left = new FormAttachment(0, 25);
		composite.setLayoutData(fd_composite);
		composite.setLayout(new GridLayout(2, false));
		
		txtEntrerLeChemin = new Text(composite, SWT.BORDER);
		txtEntrerLeChemin.setToolTipText("Entrer le chemin");
		txtEntrerLeChemin.setMessage("Entrer le chemin");
		GridData gd_txtEntrerLeChemin = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtEntrerLeChemin.widthHint = 193;
		txtEntrerLeChemin.setLayoutData(gd_txtEntrerLeChemin);
		
		btnValider = new Button(composite, SWT.NONE);
		btnValider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if(txtEntrerLeChemin.getText()!="Entrer le chemin") {
					int res=0;
					ArrayList<String> s = new ArrayList<String>();
					for(int i=0; i<state.length; i++) {
						if (state[i]) {
							s.add(ext[i]);
							res++;
						}
					}
					if(res!=0) {
						String[] array = s.toArray(new String[s.size()]);
						l = new Liste(txtEntrerLeChemin.getText(), array);
					}
						
				}
				
			}
		});
		btnValider.setText("Valider");
		
		composite_1 = new Composite(shell, SWT.BORDER);
		composite_1.setLayout(new GridLayout(3, false));
		FormData fd_composite_1 = new FormData();
		fd_composite_1.bottom = new FormAttachment(composite, 77, SWT.BOTTOM);
		fd_composite_1.top = new FormAttachment(composite, 28);
		fd_composite_1.left = new FormAttachment(composite, 0, SWT.LEFT);
		fd_composite_1.right = new FormAttachment(0, 289);
		composite_1.setLayoutData(fd_composite_1);
		
		btnChance = new Button(composite_1, SWT.NONE);
		btnChance.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				File res = l.getRandomFile();
				System.out.println(res);
				if(res.toString().contains(".mp4")){
					lecture(res.toString());
				}
				
			}
		});
		btnChance.setText("Chance");
		
		btnHistorique = new Button(composite_1, SWT.NONE);
		btnHistorique.setText("Historique");
		
		btnNettoyer = new Button(composite_1, SWT.NONE);
		btnNettoyer.setText("Nettoyer");
		
		composite_2 = new Composite(shell, SWT.BORDER);
		composite_2.setLayout(new GridLayout(3, false));
		FormData fd_composite_2 = new FormData();
		fd_composite_2.bottom = new FormAttachment(composite_1, 150, SWT.BOTTOM);
		fd_composite_2.right = new FormAttachment(0, 289);
		fd_composite_2.top = new FormAttachment(composite_1, 6);
		fd_composite_2.left = new FormAttachment(0, 25);
		composite_2.setLayoutData(fd_composite_2);
		
		btnMp4 = new Button(composite_2, SWT.CHECK);
		btnMp4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				state[7] = !state[7];
			}
		});
		btnMp4.setText("Mp4");
		
		btnExe = new Button(composite_2, SWT.CHECK);
		btnExe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				state[2] = !state[2];
			}
		});
		btnExe.setText("Exe");
		
		btnJpeg = new Button(composite_2, SWT.CHECK);
		btnJpeg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				state[4] = !state[4];
			}
		});
		btnJpeg.setText("Jpeg");
		
		btnAvi = new Button(composite_2, SWT.CHECK);
		btnAvi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				state[0] = !state[0];
			}
		});
		btnAvi.setText("Avi");
		
		btnMp3 = new Button(composite_2, SWT.CHECK);
		btnMp3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				state[6] = !state[6];
			}
		});
		btnMp3.setText("Mp3");
		new Label(composite_2, SWT.NONE);
		
		btnMkv = new Button(composite_2, SWT.CHECK);
		btnMkv.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				state[5] = !state[5];
			}
		});
		btnMkv.setText("Mkv");
		
		btnCbr = new Button(composite_2, SWT.CHECK);
		btnCbr.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				state[1] = !state[1];
			}
		});
		btnCbr.setText("Cbr");
		new Label(composite_2, SWT.NONE);
		
		btnPdf = new Button(composite_2, SWT.CHECK);
		btnPdf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				state[8] = !state[8];
			}
		});
		btnPdf.setText("Pdf");
		
		btnFlac = new Button(composite_2, SWT.CHECK);
		btnFlac.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				state[3] = !state[3];
			}
		});
		btnFlac.setText("Flac");
		new Label(composite_2, SWT.NONE);
		
		
		
		
	}
	public void lecture(String file) {
		
		/*Lecteur l = new Lecteur();
		
		*/
		JFrame frame;
	    final EmbeddedMediaPlayerComponent mediaPlayerComponent;
	    JButton pauseButton;
	    JButton rewindButton;
	    JButton skipButton;
	    
	    NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
	    
	    frame = new JFrame("My First Media Player");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);    

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	mediaPlayerComponent.mediaPlayer().controls().pause();
            }
        });

        JPanel controlsPane = new JPanel();
        pauseButton = new JButton("Pause");
        controlsPane.add(pauseButton);
        rewindButton = new JButton("Rewind");
        controlsPane.add(rewindButton);
        skipButton = new JButton("Skip");
        controlsPane.add(skipButton);
        contentPane.add(controlsPane, BorderLayout.SOUTH);

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayerComponent.mediaPlayer().controls().pause();
            }
        });

        rewindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayerComponent.mediaPlayer().controls().skipTime(-10000);
            }
        });

        skipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayerComponent.mediaPlayer().controls().skipTime(10000);
            }
        });

        frame.setContentPane(contentPane);
        frame.setVisible(true);

        mediaPlayerComponent.mediaPlayer().media().play(file);

	}
}
