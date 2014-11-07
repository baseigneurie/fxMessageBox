/*
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    
 */

/*
    This is my first attempt at creating a piece of open source software using Java and JavaFX.
  	I wrote this because I was working on another JavaFX project during my lunch breaks and I noticed
   	there wasn't a build in message dialog option for JavaFX.
    
   	I know there are better options available, but this is my first of many gifts to the open source
   	community. Please tear it apart if you will and feel free to let me know how I could have done better. ***
    
    Created 10/31/2014
    Author: Broc Seigneurie
    
 */


import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MessageBox {
	
	private final double winWidth = 550;
	private	final double winHeight = 140;
	private final int maxMsgLen = 360;
	private Stage mainStage;
	private boolean endResult;
	
    public MessageBox(String strTitle, String strMsg) {		
    	//Build window
    	mainStage = buildWindow(strTitle);
    	
    	//Build Message Text
    	HBox hbMessage = buildMessage(strMsg);
    	
    	//Build Buttons
    	HBox hbButtons = setButtons(0);
    	
    	AnchorPane anchorPane = buildPane(hbMessage, hbButtons);    	
    	
    	Scene scene = new Scene(anchorPane,winWidth,winHeight);
    	mainStage.setScene(scene);  	
    	
	}
    
    public MessageBox(String strTitle, String strMsg, String btnConfig) {	
    	int config;
    	
		//Build window
    	mainStage = buildWindow(strTitle);
    	
    	//Build Message Text
    	HBox hbMessage = buildMessage(strMsg);
    	
    	//Build Buttons
    	if (validateConfig(btnConfig)) {
    		config = findButtonConfig(btnConfig);
    	} else {
    		MessageBox err = new MessageBox("Button Error", "Please enter a correct button configuration.", "Ok");
    		return;
    	}
    	
    	HBox hbButtons = setButtons(config);
    	
    	AnchorPane anchorPane = buildPane(hbMessage, hbButtons);
    	    	
    	Scene scene = new Scene(anchorPane,winWidth,winHeight);
    	mainStage.setScene(scene);	
    	
	}
    
    public boolean show() {
    	mainStage.showAndWait();
    	return endResult;    	
    }
    
    private Stage buildWindow(String title) {
    	Stage stage = new Stage();
    	stage.setTitle(title);
    	stage.setResizable(false);
    	
    	return stage;    	
    }
    
    private HBox buildMessage(String msg) {
    	Label lblMessage = new Label();
    	
    	if (msg.length() > 360) {
    		MessageBox err = new MessageBox("Message Error", "The length of your message exceeds the default of 360"
    				+ " characters. Your message will be truncated.", "Ok");
    		err.show();
   			msg = msg.substring(0,maxMsgLen);
    	}
    	    	
    	lblMessage.setAlignment(Pos.CENTER_LEFT);
    	lblMessage.setText(msg);
    	lblMessage.setWrapText(true);
    	
    	HBox hb = new HBox();
    	hb.setPrefWidth(winWidth);
    	hb.setPadding(new Insets(5,10,5,10));
    	hb.getChildren().add(lblMessage);
    	
    	return hb;
    }
    
    private AnchorPane buildPane(HBox msg, HBox btn) {
    	AnchorPane ap = new AnchorPane();
    	ap.getChildren().addAll(msg, btn);
    	ap.setTopAnchor(msg, 10.0);
    	ap.setBottomAnchor(btn, 8.0);
    	
    	return ap;
    }
    
    private boolean validateConfig(String s) {
    	String[] options = {"Ok", "SaveCancel", "YesNoCancel"};
    	boolean result = false;
    	
    	for (String opt : options) {
			if (opt == s) {
				result = true;
			}
		}
    	
    	return result;
    }
    
    private int findButtonConfig(String s) {
    	switch(s) {
    		case "Ok":
    			return 1;
    			    		
    		case "SaveCancel":
    			return 2;
    			    		
    		case "YesNoCancel":
    			return 3;
    			    			
    		default:
    			return 0;    		
    	}
    }        
    
    private HBox setButtons(int config){
    	
    	Button btnOK = new Button();
    	Button btnCancel = new Button();
    	Button btnYes = new Button();
    	Button btnNo = new Button();
    	Button btnSave = new Button();
    	
    	HBox hb = new HBox(10);
    	double butWidth = 80;
    	double butHeight = 20;
    	hb.setPrefWidth(winWidth);
    	
    	btnCancel.setPrefSize(butWidth, butHeight);
		btnCancel.setText("Cancel");
		btnCancel.addEventHandler(ActionEvent.ACTION, event -> cancelResult());
		
		btnOK.setPrefSize(butWidth, butHeight);
		btnOK.setText("OK");
		btnOK.addEventHandler(ActionEvent.ACTION, event -> confirmResult());
    	
    	switch(config) {
    		case 0:
    			hb.getChildren().addAll(btnOK, btnCancel);
    	    	hb.setAlignment(Pos.BOTTOM_RIGHT);
    			break;
    		case 1:
    			hb.getChildren().addAll(btnOK);
    	    	hb.setAlignment(Pos.BOTTOM_RIGHT);
    			break;
    		case 2:
    			btnSave.setPrefSize(butWidth, butHeight);
    			btnSave.setText("Save");
    			btnSave.addEventHandler(ActionEvent.ACTION, event -> confirmResult());
    			hb.getChildren().addAll(btnSave, btnCancel);
    	    	hb.setAlignment(Pos.BOTTOM_RIGHT);
    			break;
    		case 3:
    			btnYes.setPrefSize(butWidth, butHeight);
    			btnYes.setText("Yes");
    			btnYes.addEventHandler(ActionEvent.ACTION, event -> confirmResult());
    			btnNo.setPrefSize(butWidth, butHeight);
    			btnNo.setText("No");
    			btnNo.addEventHandler(ActionEvent.ACTION, event -> denyResult());
    			btnCancel.setPrefSize(butWidth, butHeight);
    			btnCancel.setText("Cancel");
    			hb.getChildren().addAll(btnYes, btnNo, btnCancel);
    	    	hb.setAlignment(Pos.BOTTOM_RIGHT);
    			break;
    	}    
    	
    	return hb;
    }
    
    private void confirmResult() {
    	endResult = true;
    	mainStage.close();
    }
    
    private void denyResult() {
    	endResult = false;
    	mainStage.close();
    }
    
    private void cancelResult() {
    	mainStage.close();
    }

        

    
}
