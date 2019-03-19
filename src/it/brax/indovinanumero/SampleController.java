package it.brax.indovinanumero;

import java.util.ArrayList;
import it.brax.indovinanumero.model.IndovinaNumeroModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class SampleController {
	private int NMAX;
	private int TMAX;
	private int SEGRETO;
	private int tentativo;
	private int numTentativi;
	private ArrayList<Integer> numInseriti = new ArrayList<Integer>();
	private boolean numeroGiaInserito;
	String difficolta;
	double coefficienteDifficolta;
	int min;
	int max;
	private IndovinaNumeroModel model;

    public void setModel(IndovinaNumeroModel model) {
		this.model = model;
	}

	@FXML
    private Button btNuovaPartita;

    @FXML
    private HBox hbPartita;

    @FXML
    private TextField tfInserimento;

    @FXML
    private Button btProva;

    @FXML
    private TextArea taMessaggi;

    @FXML
    private Label lbTentativiFatti;

    @FXML
    private Label lbTentativiTotali;
    
    @FXML
    private Button btAbbandonaParita;
    
    @FXML
    private ProgressBar pbTentativi;
    
    @FXML
    private HBox hbImpostazioniPartita;
    
    @FXML
    private TextField tfNMAX;
    
    @FXML
    private ToggleGroup rdDifficoltagroup;
    
    @FXML
    private Label lbMin;
    
    @FXML
    private Label lbMax;

    @FXML
    void btNuovaPartitaOnActionHandle(ActionEvent event) {
    	try {
    		RadioButton rdDifficolta = (RadioButton) rdDifficoltagroup.getSelectedToggle();
    		difficolta = rdDifficolta.getText();
    		switch (difficolta) {
	    		case "Facile":
	    			coefficienteDifficolta = 1.15;
	    			break;
	    		case "Intermedio":
	    			coefficienteDifficolta = 1.0;
	    			break;
	    		case "Difficile":
	    			coefficienteDifficolta = 0.85;
	    			break;
    		}
    		NMAX = Integer.parseInt(tfNMAX.getText());
    		if (NMAX < 0) throw new Exception(); // viene gestita insieme alla NumberFormatException che può generare il metodo Integer.parseInt
    		SEGRETO = (int)(Math.random() * NMAX) + 1;
    		TMAX = (int)(Math.log10(NMAX)/Math.log10(2)) + 1;
    		TMAX = (int) (TMAX * coefficienteDifficolta);
    		
    		lbMin.setText("1");
    		lbMax.setText(Integer.toString(NMAX));
    		lbTentativiFatti.setText("-");
    		lbTentativiTotali.setText("-");
    		pbTentativi.setProgress(0.0);
    		hbPartita.setDisable(false);
        	taMessaggi.clear();
        	hbImpostazioniPartita.setDisable(true);
        	lbTentativiFatti.setText("0");
        	pbTentativi.setProgress(0);;
        	lbTentativiTotali.setText(Integer.toString(TMAX));
        	numTentativi = 0;
        	numInseriti.clear();
        	numeroGiaInserito = false;        	
    	} catch (Exception e) {
    		taMessaggi.setText("NMAX DEVE ESSERE UN INTERO POSITIVO");;
    	}	
    }

    @FXML
    void btProvaOnActionHAnlde(ActionEvent event) {
    	try {
	    	tentativo = Integer.parseInt(tfInserimento.getText());
	    	for (int j : numInseriti) {
	    		if (j == tentativo) {
	    			numeroGiaInserito = true;
	    			taMessaggi.appendText("NUMERO GIÀ INSERITO\n");
	    		}
	    	}
	    	if (numeroGiaInserito == false) {
	    		numTentativi++;
	    		numInseriti.add(tentativo);
	    	   	lbTentativiFatti.setText(Integer.toString(numTentativi));
		    	pbTentativi.setProgress((double)numTentativi/(double)TMAX);
		    	if (tentativo == SEGRETO) {
		    		taMessaggi.appendText("OTTIMO. Hai indovinato il numero segreto in " + numTentativi + " tentativi\nPARTITA CONCLUSA");
		    		finePartita();
		    	}
		    	else {
		    		if (numTentativi == TMAX) {
		    			taMessaggi.appendText("Hai esautito i tentativi. Il numero segreto era " + SEGRETO +"\nPARTITA CONCLUSA");
		    			finePartita();
		    		}
		    		else {
		    			if (tentativo < SEGRETO) {
		    				taMessaggi.appendText(tentativo + ": Troppo BASSO\n");
		    				lbMin.setText(Integer.toString(tentativo + 1));
		    			} else {
		    				taMessaggi.appendText(tentativo + ": Troppo ALTO\n");
		    				lbMax.setText(Integer.toString(tentativo -1));
		    			}
		    		}
		    	}
	    	}
	    	numeroGiaInserito = false;
    	} catch(NumberFormatException e) {
    		taMessaggi.appendText("NUMERO NON VALIDO\n");
    	}
    	tfInserimento.clear();
    }
    
    @FXML
    void btAbbandonaPartitaOnActionHandle(ActionEvent event) {
    	taMessaggi.appendText("PARTITA ABBANDONATA\n");
    	finePartita();
    }
    
    private void finePartita() {
    	hbPartita.setDisable(true);
    	hbImpostazioniPartita.setDisable(false);
    	lbMin.setText("");
    	lbMax.setText("");
    }

}
