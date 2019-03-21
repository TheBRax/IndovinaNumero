package it.brax.indovinanumero;

import java.util.ArrayList;
import it.brax.indovinanumero.model.IndovinaNumeroModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
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

	private String tentativo;

	String difficolta;
	double coefficienteDifficolta;
	int min;
	int max;
	private IndovinaNumeroModel model;

    public void setModel(IndovinaNumeroModel model) {
		this.model = model;
		lbTentativiTotali.textProperty().bind(Bindings.convert(model.tmaxProperty()));
		lbTentativiFatti.textProperty().bind(Bindings.convert(model.numTentativiFattiProperty()));
		lbMin.textProperty().bind(Bindings.convert(model.estremoInfProperty()));
		lbMax.textProperty().bind(Bindings.convert(model.estremoSupProperty()));
		taMessaggi.textProperty().bind(Bindings.convert(model.messaggiProperty()));
		pbTentativi.progressProperty().bind( model.pbTentativiFattiValueProperty());
		hbImpostazioniPartita.disableProperty().bind(model.inGiocoProperty());
		hbPartita.disableProperty().bind(model.inGiocoProperty().not());
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
    		NMAX = Integer.parseInt(tfNMAX.getText());
    		if (NMAX < 0) throw new Exception(); // viene gestita insieme alla NumberFormatException che può generare il metodo Integer.parseInt
    		taMessaggi.clear();
        	model.nuovaPartita(difficolta, NMAX);
    	} catch (Exception e) {
    		taMessaggi.setText("NMAX DEVE ESSERE UN INTERO POSITIVO");
    	}	
    }

    @FXML
    void btProvaOnActionHAnlde(ActionEvent event) {
    	tentativo = tfInserimento.getText();
    	model.verificaTentativo(tentativo);
    	tfInserimento.clear();
    }
    
    @FXML
    void btAbbandonaPartitaOnActionHandle(ActionEvent event) {
    	taMessaggi.appendText("PARTITA ABBANDONATA\n");
    	model.finePartita();
    }
}
