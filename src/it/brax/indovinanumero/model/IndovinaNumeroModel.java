package it.brax.indovinanumero.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class IndovinaNumeroModel {
	private IntegerProperty tmax;
	private StringProperty messaggi;
	private DoubleProperty pbTentativiFattiValue;
	
	private int nmax;
	private int segreto;
	private double coefficienteDiDifficolta;
	
	private BooleanProperty inGioco;
	
	private Set<Integer> tentativiFatti;
	private IntegerProperty numTentativiFatti;
	private IntegerProperty estremoInf;
	private IntegerProperty estremoSup;
	
	public IndovinaNumeroModel() {
		tmax = new SimpleIntegerProperty();
		numTentativiFatti = new SimpleIntegerProperty();
		tentativiFatti = new HashSet<Integer>();
		estremoInf = new SimpleIntegerProperty();
		estremoSup = new SimpleIntegerProperty();
		inGioco = new SimpleBooleanProperty();
		messaggi = new SimpleStringProperty();
		pbTentativiFattiValue =  new SimpleDoubleProperty();
	}
	
	public void nuovaPartita(String difficolta, int n) {
		inGioco.set(true);
		pbTentativiFattiValue.set(0.0);
		messaggi.set("");
		nmax = n;
		coefficienteDiDifficolta = calcolaCoefficienteDiDifficolta(difficolta);
		segreto = (int)(Math.random() * nmax) + 1;
		tmax.set((int)(( ( Math.log10(nmax)/Math.log10(2) ) + 1) * coefficienteDiDifficolta));
		
		estremoInf.set(1);
		estremoSup.set(nmax);
		
		numTentativiFatti.set(0);
		tentativiFatti.clear();		
	}
	
	public void verificaTentativo(String ts) {
		try {
			int tentativo = Integer.parseInt(ts);
			if (tentativo < 1 || tentativo > nmax) {
				messaggi.set(messaggi.get() + "INTERO FUORI RANGE\n");
			}
			else {
				if (tentativiFatti.contains(tentativo)) {
					messaggi.set(messaggi.get() + "INTERO GI� INSERITO\n");
				}
				else {
					tentativiFatti.add(tentativo);
					numTentativiFatti.set(numTentativiFatti.get() + 1);
					pbTentativiFattiValue.set((double)numTentativiFatti.get()/tmax.get());
					if (numTentativiFatti.get() <= tmax.get()) {
				
						if (tentativo == segreto) {
							messaggi.set(messaggi.get() + "Ottimo. Hai indovinato il numero segreto in " + numTentativiFatti.get() + " tentativi.");
							finePartita();
						} else if (tentativo < segreto) {
							messaggi.set(messaggi.get() + numTentativiFatti.get() + ". " + tentativo + ": Troppo Basso\n" );
							estremoInf.set(tentativo+1 >= estremoInf.get() ? tentativo+1 : estremoInf.get() );
						} else if (tentativo > segreto) {
							messaggi.set(messaggi.get() + numTentativiFatti.get() + ". " + tentativo + ": Troppo Alto\n" );
							estremoSup.set(tentativo-1 <= estremoSup.get() ? tentativo-1 : estremoSup.get() );
						}
					} else {			
						messaggi.set(messaggi.get() + "Hai esaurito i tenativi. Il numero segreto era " + segreto);
						finePartita();	
					}
				}
				
			}
		} catch (NumberFormatException e) {
			messaggi.set(messaggi.get() + "NUMERO NON VALIDO\n");
		}
	}
	
	private double calcolaCoefficienteDiDifficolta (String s) {
		if (s.equals("Facile")) return 1.25;
		else if (s.contentEquals("Intermedio")) return 1.00;
		else return 0.85;
	}
	
	public void stampaMessaggioErrore(String s) {
		messaggi.set("");
		messaggi.set(messaggi.get().concat(s));
	}
	
	public void finePartita() {
		inGioco.set(false);
	}
	
	public final IntegerProperty tmaxProperty() {
		return this.tmax;
	}
	

	public final int getTmax() {
		return this.tmaxProperty().get();
	}
	

	public final void setTmax(final int tmax) {
		this.tmaxProperty().set(tmax);
	}

	public final IntegerProperty numTentativiFattiProperty() {
		return this.numTentativiFatti;
	}
	

	public final int getNumTentativiFatti() {
		return this.numTentativiFattiProperty().get();
	}
	

	public final void setNumTentativiFatti(final int numTentativiFatti) {
		this.numTentativiFattiProperty().set(numTentativiFatti);
	}

	public final BooleanProperty inGiocoProperty() {
		return this.inGioco;
	}
	

	public final boolean isInGioco() {
		return this.inGiocoProperty().get();
	}
	

	public final void setInGioco(final boolean inGioco) {
		this.inGiocoProperty().set(inGioco);
	}

	public final IntegerProperty estremoInfProperty() {
		return this.estremoInf;
	}
	

	public final int getEstremoInf() {
		return this.estremoInfProperty().get();
	}
	

	public final void setEstremoInf(final int estremoInf) {
		this.estremoInfProperty().set(estremoInf);
	}
	

	public final IntegerProperty estremoSupProperty() {
		return this.estremoSup;
	}
	

	public final int getEstremoSup() {
		return this.estremoSupProperty().get();
	}
	

	public final void setEstremoSup(final int estremoSup) {
		this.estremoSupProperty().set(estremoSup);
	}

	public final StringProperty messaggiProperty() {
		return this.messaggi;
	}
	

	public final String getMessaggi() {
		return this.messaggiProperty().get();
	}
	

	public final void setMessaggi(final String messaggi) {
		this.messaggiProperty().set(messaggi);
	}

	public final DoubleProperty pbTentativiFattiValueProperty() {
		return this.pbTentativiFattiValue;
	}
	

	public final double getPbTentativiFattiValue() {
		return this.pbTentativiFattiValueProperty().get();
	}
	

	public final void setPbTentativiFattiValue(final double pbTentativiFattiValue) {
		this.pbTentativiFattiValueProperty().set(pbTentativiFattiValue);
	}
	
	
	
	
	
	



	
	
	

	


	
	
}
