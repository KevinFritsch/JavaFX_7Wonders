package View;
	
import java.util.ArrayList;

import Controller.Jeu;
import Model.Categorie;
import Model.Joueur;
import Model.UV;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Vue extends Application {
	private Jeu controllerJeu = null; //controller du jeu

	//variables globales utilisées dans différentes fonctions
	Scene initJoueurs, sceneJeu;
	Stage primaryStage;
	BorderPane jeuPane = new BorderPane();
	Label lblMessageErreurJeu = new Label();
	Button btnAmeliorer = new Button("Améliorer mon bâtiment");
	Label lblProchaineRecompense = new Label("Prochaine Récompense d'amélioration : ");
	HBox hboxAutresJoueurs = new HBox();
	Label lblNomJoueurCourant = new Label();
	ListView<Categorie> listeCartesJoueurCourant = new ListView<Categorie>();
	ListView<UV> listeRessourcesJoueurCourant = new ListView<UV>();
	Label lblAgeActuel = new Label("Age actuel : ");
	Label lblTourActuel = new Label("Tour actuel : ");
	Label lblSensRotation = new Label();
	ListView<String> listeClassement = new ListView<String>();
	Label lblNomBatiment = new Label();
	Label lblNiveauAmeliorationBatiment = new Label("Niveau d'amélioration : ");

	/***
	 * Méthode principale qui contient les actions et configurations des éléments graphiques de l'application
	 */
	@Override
	public void start(Stage primaryStag) {
		this.primaryStage = primaryStag;
		
		//Appel du singleton Controller
		Jeu.setUpSingleton(this);
		this.controllerJeu = Jeu.getInstanceJeu();
		
		//init pane
		BorderPane paneInitJoueurs = new BorderPane();
		paneInitJoueurs.setId("paneInit");
		HBox hboxInitJoueurs = new HBox();
		hboxInitJoueurs.setId("hbox");
		Button btnAddJoueur = new Button("Ajouter");
		Button btnDeleteJoueur = new Button("Supprimer");
		TextField txtAddJoueur = new TextField();
		Button btnCommencerJeu = new Button("Jouer");
		Button btnQuitterJeu = new Button("Quitter");
		Label lblTitreJeu = new Label("7Wonders");
		lblTitreJeu.setId("title");
		Label lblInitJoueur = new Label("Veuillez entrer le nom des joueurs");
		lblInitJoueur.setId("lblInitJoueurs");
		ListView<String> listJoueurs = new ListView<String>();
		VBox vboxAddJoueur = new VBox();
		vboxAddJoueur.setId("vbox");
		HBox hboxAddJoueur = new HBox();
		hboxAddJoueur.setId("hbox");
		HBox hboxBtnAddJoueur = new HBox();
		hboxBtnAddJoueur.setId("hbox");
		Label lblMessageErreur = new Label();
		
		
		//jeu pane
		HBox hboxButtonsJeu = new HBox();
		hboxButtonsJeu.setId("hbox");
		Button btnDefausser = new Button("Défausser la carte");
		Button btnValider = new Button("Valider la catégorie");
		lblNiveauAmeliorationBatiment.setId("lbl");
		
		hboxAutresJoueurs.setId("hboxAutresJoueurs");
		VBox vboxInfosJoueurCourant = new VBox();
		vboxInfosJoueurCourant.setId("vboxJoueurCourant");
		HBox listesJoueurCourant = new HBox();
		listesJoueurCourant.setId("hbox");
		lblNomJoueurCourant.setId("labelNomJoueur");
		VBox mesCartes = new VBox();
		mesCartes.setId("vbox");
		mesCartes.setMinWidth(650);
		
		VBox mesRessources = new VBox();
		mesRessources.setId("vbox");
		
		listeCartesJoueurCourant.setId("listeCartes");
		listeCartesJoueurCourant.setMinWidth(800);
		listeRessourcesJoueurCourant.setId("listeRessources");
		listeRessourcesJoueurCourant.setMinHeight(250);
		
		Label lblMesCartes = new Label("Mes Cartes :");
		lblMesCartes.setId("lblMonBatiment");
		Label lblMesRessources = new Label("Mes Ressources du Bâtiment :");
		lblMesRessources.setId("lbl");
		
		lblTourActuel.setId("lblInfosJeu");
		lblAgeActuel.setId("lblInfosJeu");
		lblSensRotation.setId("lblInfosJeu");
		
		Label lblInfosJeu = new Label("Avancée de la partie :");
		lblInfosJeu.setId("titreInfosJeu");
		listeClassement.setId("listeClassement");
		Label lblClassement = new Label("Classement :");
		lblClassement.setId("titreInfosJeu");
		VBox vboxJeuInformations = new VBox();
		vboxJeuInformations.setId("vboxJeuInfos");
		
		VBox vboxBatiment = new VBox();
		lblMessageErreurJeu.setId("MessageErreurJeu");
		
		VBox vboxCentre = new VBox();

		try {
			//éléments graphiques de la fenêtre d'initialisation du jeu
			paneInitJoueurs.setTop(lblTitreJeu);
			hboxAddJoueur.getChildren().addAll(lblInitJoueur, txtAddJoueur);
			hboxBtnAddJoueur.getChildren().addAll(btnAddJoueur, btnDeleteJoueur);
			vboxAddJoueur.getChildren().addAll(hboxAddJoueur, hboxBtnAddJoueur, listJoueurs, lblMessageErreur);
			paneInitJoueurs.setCenter(vboxAddJoueur);
			hboxInitJoueurs.getChildren().addAll(btnCommencerJeu, btnQuitterJeu);
			paneInitJoueurs.setBottom(hboxInitJoueurs);
			this.initJoueurs = new Scene(paneInitJoueurs, 600, 600);
			initJoueurs.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			
			//Evènements des différents bouttons de l'application
			btnCommencerJeu.setOnAction(event ->{		
				int nombreJoueurs = listJoueurs.getItems().size();
				if(nombreJoueurs < 3 || nombreJoueurs > 7) {
					lblMessageErreur.setText("Le jeu peut accueillir 3 à 7 joueurs !");
				}else {
					lblMessageErreur.setText("");
					controllerJeu.setNombreJoueurs(nombreJoueurs);
					for(String joueur : listJoueurs.getItems()) {
						controllerJeu.addJoueur(joueur);
					}
					controllerJeu.setJoueurCourant(controllerJeu.getListeJoueurs().get(0));
					actualiserAffichage();
					
					this.sceneJeu = new Scene(jeuPane);
					sceneJeu.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					primaryStage.setScene(sceneJeu);
					primaryStage.setMaximized(true);
					primaryStage.setResizable(true);
				}
			});
			btnQuitterJeu.setOnAction(event -> {
				Platform.exit();
			});
			btnAddJoueur.setOnAction(event ->{
				if(txtAddJoueur.getText() != null && !txtAddJoueur.getText().isEmpty()) {
					if(listJoueurs.getItems().contains(txtAddJoueur.getText())) {
						lblMessageErreur.setText("Le nom " + txtAddJoueur.getText() + " exite déjà!");
					}else {
						listJoueurs.getItems().add(txtAddJoueur.getText());
						lblMessageErreur.setText("");
					}
					txtAddJoueur.clear();
					
				}else {
					lblMessageErreur.setText("Veuillez entrer le nom d'un joueur");
				}
			});
			btnDeleteJoueur.setOnAction(event ->{
				final int selectedIdx = listJoueurs.getSelectionModel().getSelectedIndex();	
				try {
					listJoueurs.getItems().remove(selectedIdx);
				}catch(Exception e) {
					lblMessageErreur.setText("Veuillez selectionner un joueur pour le supprimer");
				}
			});
			txtAddJoueur.setOnKeyReleased(event -> {
				if (event.getCode() == KeyCode.ENTER){
					if(txtAddJoueur.getText() != "") {
						if(listJoueurs.getItems().contains(txtAddJoueur.getText())) {
							lblMessageErreur.setText("Le nom " + txtAddJoueur.getText() + " exite déjà!");
						}else {
							listJoueurs.getItems().add(txtAddJoueur.getText());
							lblMessageErreur.setText("");
						}
						txtAddJoueur.clear();
					}
				 }
			});
			btnDefausser.setOnAction(event ->{
				if(!listeCartesJoueurCourant.getSelectionModel().isEmpty()) {
					clearRessourcesTemp(controllerJeu.getJoueurCourant());
					final int selectedIdx = listeCartesJoueurCourant.getSelectionModel().getSelectedIndex();
					controllerJeu.getJoueurCourant().defausserCarte(listeCartesJoueurCourant.getItems().get(selectedIdx));
					controllerJeu.passageJoueurSuivant();
					actualiserAffichage();
					//lblMessageErreurJeu.setText("Carte défaussée");
				}else {
					lblMessageErreurJeu.setText("Veuillez selectionner une carte à défausser");
				}
						
			});
			btnAmeliorer.setOnAction(event ->{
				if(!listeCartesJoueurCourant.getSelectionModel().isEmpty()) {
					clearRessourcesTemp(controllerJeu.getJoueurCourant());
					final int selectedIdx = listeCartesJoueurCourant.getSelectionModel().getSelectedIndex();
					controllerJeu.getJoueurCourant().ameliorerBatiment(listeCartesJoueurCourant.getItems().get(selectedIdx));
					controllerJeu.passageJoueurSuivant();
					actualiserAffichage();
					//lblMessageErreurJeu.setText("Bâtiment amélioré");

				}else {
					lblMessageErreurJeu.setText("Veuillez selectionner une carte pour améliorer votre bâtiment");
				}
			});
			btnValider.setOnAction(event ->{
				if(!listeCartesJoueurCourant.getSelectionModel().isEmpty()) {
					final int selectedIdx = listeCartesJoueurCourant.getSelectionModel().getSelectedIndex();
					boolean verif = controllerJeu.getJoueurCourant().validerCategorie(listeCartesJoueurCourant.getItems().get(selectedIdx));
					if(verif) {
						clearRessourcesTemp(controllerJeu.getJoueurCourant());
						controllerJeu.passageJoueurSuivant();
						//lblMessageErreurJeu.setText("Catégorie validée");
						actualiserAffichage();
					}else {
						lblMessageErreurJeu.setText("Vous n'avez pas les ressources suffisantes pour valider la catégorie");
					}
				}else {
					lblMessageErreurJeu.setText("Veuillez selectionner une carte à valider");
				}
				
			});
			
			//éléments graphiques de la fenêtre de jeu
			hboxButtonsJeu.getChildren().addAll(btnDefausser, btnAmeliorer, btnValider);
			
			
	        
			vboxJeuInformations.getChildren().addAll(lblInfosJeu, lblAgeActuel, lblTourActuel, lblSensRotation, lblClassement, listeClassement);
			listeClassement.setMinHeight(600);
			listeClassement.setMinWidth(400);
			
			mesCartes.getChildren().addAll(lblMesCartes, listeCartesJoueurCourant);
			mesCartes.setId("vboxCartesBati");
			mesRessources.getChildren().addAll(lblMesRessources,listeRessourcesJoueurCourant);
			listesJoueurCourant.getChildren().addAll(mesCartes, vboxBatiment);
			vboxBatiment.setId("vboxCartesBati");
			vboxInfosJoueurCourant.getChildren().addAll(lblNomJoueurCourant, listesJoueurCourant,lblMessageErreurJeu, hboxButtonsJeu);
			vboxBatiment.getChildren().addAll(lblNomBatiment, lblNiveauAmeliorationBatiment, lblProchaineRecompense, mesRessources);
			vboxCentre.getChildren().addAll(hboxAutresJoueurs, vboxInfosJoueurCourant);
			jeuPane.setRight(vboxJeuInformations);
			jeuPane.setCenter(vboxCentre);
			
			//configuration de primaryStage
			primaryStage.setScene(initJoueurs);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	ArrayList<UV> listeRessourcesTemp = new ArrayList<UV>();
	public void clearRessourcesTemp(Joueur joueur) {
		if(listeRessourcesTemp.size() != 0) {
			for(UV uv : listeRessourcesTemp) {
				joueur.getPossedeBatimentUTBM().getRessources().remove(uv);
			}
			listeRessourcesTemp.clear();
		}
	}
	
	public void afficherNouvelAge() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Changement d'âge");
		alert.setHeaderText("A présent l'âge courant est  " + controllerJeu.getAgeActuel());
		alert.show();
		
		
	}
	public void afficherNouveauTour() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Changement de tour");
		alert.setHeaderText("A présent le tour courant est  " + controllerJeu.getTourActuel());
		alert.showAndWait();
	      
	}
	/**
	 * Méthode qui permet d'actualiser l'affichage principal du jeu
	 */
	public void actualiserAffichage() {
		lblMessageErreurJeu.setText("");
		hboxAutresJoueurs.getChildren().clear();
		
		
		
		//affichage dynamique lié aux voisins du joueur courant
		for(Joueur autre : controllerJeu.getListeVoisins()) {
			Label lblNomAutre = new Label(autre.getNomJoueur());
			lblNomAutre.setId("labelNomJoueur");
			ListView<UV> listRessources = new ListView<UV>();
			VBox vboxAutre = new VBox();
			vboxAutre.setId("vbox");
			Button btnAchatRessourceVoisin = new Button("Achat UV");
			listRessources.getItems().clear();
			listRessources.getItems().addAll(controllerJeu.getRessourcesJoueur(autre));
			vboxAutre.getChildren().addAll(lblNomAutre, listRessources,btnAchatRessourceVoisin);
			hboxAutresJoueurs.getChildren().add(vboxAutre);
			listRessources.setId("listeRessources");

			btnAchatRessourceVoisin.setOnAction(event -> {
				if(!listRessources.getSelectionModel().isEmpty()) {
					final int selectedIdx = listRessources.getSelectionModel().getSelectedIndex();
					boolean verif = controllerJeu.achatRessourcesVoisins(autre, listRessources.getItems().get(selectedIdx));
					if(verif) {
						actualiserAffichage();
						lblMessageErreurJeu.setText("UV achetée");
						listeRessourcesTemp.add(listRessources.getItems().get(selectedIdx));

					}else {
						lblMessageErreurJeu.setText("Vous n'avez pas assez de pièces d'argent pour acheter l'UV");
					}
				}else {
					lblMessageErreurJeu.setText("Veuillez selectionner une UV à acheter");
				}
			});
		}
		
		
		//affichages liés au bâtiment du joueur courant
		if(controllerJeu.getJoueurCourant().getPossedeBatimentUTBM().getNiveauAmelioration() == 3) {
			btnAmeliorer.setDisable(true);
			lblProchaineRecompense.setVisible(false);
		}else {
			btnAmeliorer.setDisable(false);
			lblProchaineRecompense.setVisible(true);
		}
		
		//affichages liés aux informations du joueur courant
		lblNomJoueurCourant.setText("Joueur courant : " + controllerJeu.getJoueurCourant().getNomJoueur() + " (Pièces : " + controllerJeu.getJoueurCourant().getArgent() + ", Crédits ECTS : " +
				controllerJeu.getJoueurCourant().getCreditsECTS() + ")");
		listeCartesJoueurCourant.getItems().clear();
		listeCartesJoueurCourant.getItems().addAll(controllerJeu.getJoueurCourant().getMainCartes().getListeCartes());
		listeRessourcesJoueurCourant.getItems().clear();
		
		listeRessourcesJoueurCourant.getItems().addAll(controllerJeu.getRessourcesJoueur(controllerJeu.getJoueurCourant()));
		
		int age = controllerJeu.getAgeActuel();
		String ageUTBM = "Tronc-Commun";
		if(age == 2) {
			ageUTBM = "Branche";
		}else if(age == 3) {
			ageUTBM = "Spécialisation";
		}
		lblAgeActuel.setText("Age actuel : " + controllerJeu.getAgeActuel() + " (" + ageUTBM + ")");
		
		
		
		lblTourActuel.setText("Tour actuel : " + controllerJeu.getTourActuel());
		listeClassement.getItems().clear();
		listeClassement.getItems().addAll(controllerJeu.getClassement());
		lblNomBatiment.setText("Mon Bâtiment : " + controllerJeu.getJoueurCourant().getPossedeBatimentUTBM().getNomBatimentEnum());
		lblNomBatiment.setId("lblMonBatiment");
		lblNiveauAmeliorationBatiment.setText("Niveau d'amélioration : " + controllerJeu.getJoueurCourant().getPossedeBatimentUTBM().getNiveauAmelioration() + "/3");
		
		lblProchaineRecompense.setId("lbl");
		//si le niveau d'amélioration du bâtiment est maximal, on affiche plus les récompenses d'amélioration
		if(controllerJeu.getJoueurCourant().getPossedeBatimentUTBM().getNiveauAmelioration() != 3) {
			lblProchaineRecompense.setText("Prochaine récompense d'amélioration : " + controllerJeu.getJoueurCourant().getPossedeBatimentUTBM().getGainAmeliorationBatiment());
		}
		if(controllerJeu.isEchangeSensHoraire()) {
			lblSensRotation.setText("Sens : Horaire");
		}else {
			lblSensRotation.setText("Sens : Anti-Horaire");
		}
		
		
	}
	/***
	 * Méthode permettant d'afficher le classement des joueurs selon leur nombre de crédits ECTS
	 */
	public void afficheClassement() {
		jeuPane.setDisable(true);//cela permet de bloquer la fenêtre de jeu principale
		
		//Button btnRejouer = new Button("Rejouer");
		Button btnQuitter = new Button("Quitter");
		
		btnQuitter.setOnAction(event ->{
			Platform.exit();
		});
		HBox hboxButtonsFin = new HBox();
		hboxButtonsFin.getChildren().addAll(btnQuitter);
		
		//on crée une nouvelle fenêtre pour afficher le classemet final des joueurs
		Stage stageClassement = new Stage();
		stageClassement.initModality(Modality.APPLICATION_MODAL);
		stageClassement.setMinWidth(700);
		stageClassement.setMinHeight(700);
		ListView<String> classementJoueurs = new ListView<String>();
		classementJoueurs.setId("listeClassement");
		classementJoueurs.getItems().addAll(controllerJeu.getClassement());
		BorderPane paneClassement = new BorderPane();
		VBox vboxInfos = new VBox();
		vboxInfos.setId("vbox");
		Label lblTitle = new Label("Classement final");
		lblTitle.setId("title");
		
		vboxInfos.getChildren().addAll(lblTitle, classementJoueurs, hboxButtonsFin);
		paneClassement.setCenter(vboxInfos);

		//Scene qui représente la fenêtre d'affichage du classement
		Scene sceneClassement = new Scene(paneClassement);
		sceneClassement.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stageClassement.setScene(sceneClassement);
		stageClassement.show();
	}

	public void finPartie() {
		afficheClassement();
	}
	
	/**
	 * Méthode static Main permettant de lancer l'application
	 * @param args Stage représentant la fenêtre par défaut de l'application
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
