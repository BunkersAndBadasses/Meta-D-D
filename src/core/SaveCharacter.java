package core;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SaveCharacter {

    private static Document doc;
    private static File stocks;
    private static Document element;
    private static character c = Main.gameState.currentlyLoadedCharacter;
    private Element ele;
    private String temp;
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    
    

    public SaveCharacter(Boolean New, String fn) {
        try {
            // New character file
            if (New) {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                // root elements
                Document newDoc = docBuilder.newDocument();
                Element Character = newDoc.createElement("Character");
                newDoc.appendChild(Character);
                
                appendValue("Name", c.getName());
                appendValue("Level", c.getLevel());
                appendValue("EXP", c.getExp());
                appendValue("Race", c.getCharRace().getName());
                appendValue("Alignment", c.getAlignment());
                
                appendValue("Deity", c.getDeity());
                appendValue("Size", c.getSize());
                appendValue("Age", c.getAge());
                appendValue("Gender", c.getGender());
                appendValue("Height", c.getHeight());
                
                appendValue("Weight", c.getWeight());
                appendValue("Eyes", c.getEyes());
                appendValue("Hair", c.getHair());
                appendValue("Skin", c.getSkin());
                appendValue("Description", c.getDescription());
                
                temp = "";
                for(int i = 0; i < c.getSpells().size(); i++){
                    temp += c.getSpells().get(i).getName() + "/";
                }
                appendValue("Spells", temp);
                temp = "";
                for(int i = 0; i < c.getSpecialAbilities().size(); i++){
                    temp += c.getSpecialAbilities().get(i).getName() + "/";
                }
                appendValue("SpecialAbilities", temp);
                temp = "";
                for(int i = 0; i < c.getPrepSpells().size(); i++){
                    temp += c.getPrepSpells().get(i).getName() + "/";
                }
                appendValue("PreparedSpells", temp);
                appendValue("AC", c.getAC());
                appendValue("TouchAC", c.getTouchAC());
                
                appendValue("FlatFootedAC", c.getFlatFootedAC());
                appendValue("Init", c.getInitMod());
                appendValue("Fortitude", c.getSavingThrows()[0]);
                appendValue("Reflex", c.getSavingThrows()[1]);
                appendValue("Will", c.getSavingThrows()[2]);
                
                appendValue("BaseAttack", c.getBaseAttackBonus());
                appendValue("SpellResistance", c.getSpellResistance());
                appendValue("Speed", c.getSpeed());
                appendValue("Grapple", c.getGrappleMod());
                appendValue("DamageReduction", c.getDamageReduction());
                
                appendValue("ClericDomains", c.getName());//
                appendValue("DruidCompanion", c.getDruidAnimalCompanion());
                appendValue("RangerFavoredEnemy", c.getRangerFavoredEnemy());
                appendValue("Familiar", c.getFamiliar());
                appendValue("WizardSpecialty", c.getWizardSpecialtySchool());
                
                temp = "";
                for(int i = 0; i < c.getWizardProhibitedSchools().length; i++){
                    temp += c.getWizardProhibitedSchools()[i]+ "/";
                }
                appendValue("WizardProhibitedSchools", temp);
                appendValue("Image", c.getImage());
                appendValue("Class", c.getClass().getName());
                appendValue("SecLevel", c.getSecLevel());
                appendValue("SecClass", c.getSecClass().getName());
                
                appendValue("STR", c.getAbilityModifiers()[0]);
                appendValue("DEX", c.getAbilityModifiers()[1]);
                appendValue("CON", c.getAbilityModifiers()[2]);
                appendValue("INT", c.getAbilityModifiers()[3]);
                appendValue("WIS", c.getAbilityModifiers()[4]);
                
                appendValue("CHA", c.getAbilityModifiers()[5]);
                appendValue("HP", c.getHitPoints());
                appendValue("Speed", c.getSpeed());
                appendValue("PrimaryWeapon", c.getPrimary().getName());
                appendValue("SecondaryWeapon", c.getSecondary().getName());
                
                appendValue("Armor", c.getCurrArmor().getName());
                appendValue("Shield", c.getShield().getName());
                appendValue("Notes", c.getNotes());
                appendValue("DamageTaken", c.getDamageTaken());
                appendValue("PP", c.getPP());
                
                appendValue("GP", c.getgold());
                appendValue("SP", c.getSP());
                appendValue("CP", c.getCP());
                temp = "";
                for(int i = 0; i < c.getItems().size(); i++){
                    temp += c.getItems().get(i).getName() + c.getItems().get(i).getCount() + "/";
                }
                appendValue("Items", temp);
                temp = "";
                for(int i = 0; i < c.getLanguages().size(); i++){
                    temp += c.getLanguages().get(i) + "/";
                }
                appendValue("Languages", temp);
                
                temp = "";
                for(int i = 0; i < c.getWeapons().size(); i++){
                    temp += c.getWeapons().get(i).getName() + c.getWeapons().get(i).getQuantity() + "/";

                }
                appendValue("Weapons", temp); 
                temp = "";
                for(int i = 0; i < c.getArmor().size(); i++){
                    temp += c.getArmor().get(i).getName() + c.getArmor().get(i).getQuantity() + "/";
                }
                appendValue("Armors", temp); 
                temp = "";
                for(int i = 0; i < c.getSkills().size(); i++){
                    temp += c.getSkills().get(i).getSkill().getName() + c.getArmor().get(i).getQuantity() + "/";
                }
                appendValue("Skills", temp);
                temp = "";
                for(int i = 0; i < c.getShields().size(); i++){
                    temp += c.getShields().get(i).getName() +c.getSkills().get(i).getRank() +  "/";
                }
                appendValue("Shields", temp);
                temp = "";
                for(int i = 0; i < c.getFeats().size(); i++){
                    temp += c.getFeats().get(i).getName() + c.getShields().get(i).getQuantity() + "/";
                }
                appendValue("Feats", temp);
                
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(newDoc);

                //change back to character.getName() is not working correctly
                String charName = c.getName().replaceAll("[^A-Za-z0-9]", "");
                try{
                    File CHARACTER = new File(System.getProperty("user.dir") + "//" + "User Data" + "//" + "Character");
                    CHARACTER.mkdir();
                    File CHARDIR = new File(CHARACTER.getPath() + "//" + charName);
                    CHARDIR.mkdir();
                    StreamResult result = new StreamResult(CHARDIR.getPath() + "//" + charName + ".xml");


                    transformer.transform(source, result);
                }catch(Exception e){
                    File CHARDIR = new File(System.getProperty("user.dir") + "//" + "User Data" + "//DND" + charName);
                    CHARDIR.mkdir();
                    StreamResult result = new StreamResult(CHARDIR.getPath() + "//DND" + charName + ".xml");

                    transformer.transform(source, result);
                }
           
            }
            // Not a new Character
            else {
                String filename = fn;
                stocks = new File(filename);
                dbFactory = DocumentBuilderFactory.newInstance();
                dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.parse(stocks);
                doc.getDocumentElement().normalize();

                NodeList nodes = doc.getElementsByTagName("Character");

                Node node = nodes.item(0);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    writeValue("Name", c.getName());
                    writeValue("Level", c.getLevel());
                    writeValue("EXP", c.getExp());
                    writeValue("Race", c.getCharRace().getName());
                    writeValue("Alignment", c.getAlignment());
                    
                    writeValue("Deity", c.getDeity());
                    writeValue("Size", c.getSize());
                    writeValue("Age", c.getAge());
                    writeValue("Gender", c.getGender());
                    writeValue("Height", c.getHeight());
                    
                    writeValue("Weight", c.getWeight());
                    writeValue("Eyes", c.getEyes());
                    writeValue("Hair", c.getHair());
                    writeValue("Skin", c.getSkin());
                    writeValue("Description", c.getDescription());
                    
                    temp = "";
                    for(int i = 0; i < c.getSpells().size(); i++){
                        temp += c.getSpells().get(i).getName() + "/";
                    }
                    writeValue("Spells", temp);
                    temp = "";
                    for(int i = 0; i < c.getSpecialAbilities().size(); i++){
                        temp += c.getSpecialAbilities().get(i).getName() + "/";
                    }
                    writeValue("SpecialAbilities", temp);
                    temp = "";
                    for(int i = 0; i < c.getPrepSpells().size(); i++){
                        temp += c.getPrepSpells().get(i).getName() + "/";
                    }
                    writeValue("PreparedSpells", temp);
                    writeValue("AC", c.getAC());
                    writeValue("TouchAC", c.getTouchAC());
                    
                    writeValue("FlatFootedAC", c.getFlatFootedAC());
                    writeValue("Init", c.getInitMod());
                    writeValue("Fortitude", c.getSavingThrows()[0]);
                    writeValue("Reflex", c.getSavingThrows()[1]);
                    writeValue("Will", c.getSavingThrows()[2]);
                    
                    writeValue("BaseAttack", c.getBaseAttackBonus());
                    writeValue("SpellResistance", c.getSpellResistance());
                    writeValue("Speed", c.getSpeed());
                    writeValue("Grapple", c.getGrappleMod());
                    writeValue("DamageReduction", c.getDamageReduction());
                    
                    writeValue("ClericDomains", c.getName());//
                    writeValue("DruidCompanion", c.getDruidAnimalCompanion());
                    writeValue("RangerFavoredEnemy", c.getRangerFavoredEnemy());
                    writeValue("Familiar", c.getFamiliar());
                    writeValue("WizardSpecialty", c.getWizardSpecialtySchool());
                    
                    temp = "";
                    for(int i = 0; i < c.getWizardProhibitedSchools().length; i++){
                        temp += c.getWizardProhibitedSchools()[i]+ "/";
                    }
                    writeValue("WizardProhibitedSchools", temp);
                    writeValue("Image", c.getImage());
                    writeValue("Class", c.getClass().getName());
                    writeValue("SecLevel", c.getSecLevel());
                    writeValue("SecClass", c.getSecClass().getName());
                    
                    writeValue("STR", c.getAbilityModifiers()[0]);
                    writeValue("DEX", c.getAbilityModifiers()[1]);
                    writeValue("CON", c.getAbilityModifiers()[2]);
                    writeValue("INT", c.getAbilityModifiers()[3]);
                    writeValue("WIS", c.getAbilityModifiers()[4]);
                    
                    writeValue("CHA", c.getAbilityModifiers()[5]);
                    writeValue("HP", c.getHitPoints());
                    writeValue("Speed", c.getSpeed());
                    writeValue("PrimaryWeapon", c.getPrimary().getName());
                    writeValue("SecondaryWeapon", c.getSecondary().getName());
                    
                    writeValue("Armor", c.getCurrArmor().getName());
                    writeValue("Shield", c.getShield().getName());
                    writeValue("Notes", c.getNotes());
                    writeValue("DamageTaken", c.getDamageTaken());
                    writeValue("PP", c.getPP());
                    
                    writeValue("GP", c.getgold());
                    writeValue("SP", c.getSP());
                    writeValue("CP", c.getCP());
                    temp = "";
                    for(int i = 0; i < c.getItems().size(); i++){
                        temp += c.getItems().get(i).getName() + c.getItems().get(i).getCount() + "/";
                    }
                    writeValue("Items", temp);
                    temp = "";
                    for(int i = 0; i < c.getLanguages().size(); i++){
                        temp += c.getLanguages().get(i) + "/";
                    }
                    writeValue("Languages", temp);
                    
                    temp = "";
                    for(int i = 0; i < c.getWeapons().size(); i++){
                        temp += c.getWeapons().get(i).getName() + c.getWeapons().get(i).getQuantity() + "/";

                    }
                    writeValue("Weapons", temp); 
                    temp = "";
                    for(int i = 0; i < c.getArmor().size(); i++){
                        temp += c.getArmor().get(i).getName() + c.getArmor().get(i).getQuantity() + "/";
                    }
                    writeValue("Armors", temp); 
                    temp = "";
                    for(int i = 0; i < c.getSkills().size(); i++){
                        temp += c.getSkills().get(i).getSkill().getName() + c.getArmor().get(i).getQuantity() + "/";
                    }
                    writeValue("Skills", temp);
                    temp = "";
                    for(int i = 0; i < c.getShields().size(); i++){
                        temp += c.getShields().get(i).getName() +c.getSkills().get(i).getRank() +  "/";
                    }
                    writeValue("Shields", temp);
                    temp = "";
                    for(int i = 0; i < c.getFeats().size(); i++){
                        temp += c.getFeats().get(i).getName() + c.getShields().get(i).getQuantity() + "/";
                    }
                    writeValue("Feats", temp);
                }

            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public SaveCharacter(Boolean New) {
        this(New, c.getFilename());
    }

    private static boolean writeValue(String tag, String value){
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        node.setTextContent(value);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(stocks.getAbsolutePath());
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
    
    private static boolean writeValue(String tag, int value) {
        return writeValue(tag, Integer.toString(value));
    }

    private boolean appendValue(String tag, String value) {
        Element temp = doc.createElement(tag);
        temp.appendChild(doc.createTextNode(value));
        ele.appendChild(temp);
        return true;
    }
    
    private boolean appendValue(String tag, int value) {
        return appendValue(tag, Integer.toString(value));
    }

}