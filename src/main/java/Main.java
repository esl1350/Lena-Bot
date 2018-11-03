import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import java.awt.Color;
import javax.security.auth.login.LoginException;
import java.util.HashMap;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.util.Iterator;

public class Main extends ListenerAdapter{
    private static HashMap<String, Unit> myHeroBase;
    static {
        try {
            myHeroBase = new HashMap<>();
            Workbook workbook = WorkbookFactory.create(new File("data.xlsx"));
            DataFormatter df = new DataFormatter();
            for(int i = 0; i < 105; i++){
                Unit newUnit = new Unit();
                Cell new1 = workbook.getSheetAt(0).getRow(i).getCell(0);
                newUnit.category = df.formatCellValue(new1);

                new1 = workbook.getSheetAt(0).getRow(i).getCell(1);
                newUnit.rarity = df.formatCellValue(new1);

                new1 = workbook.getSheetAt(0).getRow(i).getCell(2);
                newUnit.unitType = df.formatCellValue(new1);

                new1 = workbook.getSheetAt(0).getRow(i).getCell(3);
                newUnit.element = df.formatCellValue(new1);

                new1 = workbook.getSheetAt(0).getRow(i).getCell(4);
                newUnit.name = df.formatCellValue(new1);

                new1 = workbook.getSheetAt(0).getRow(i).getCell(5);
                newUnit.subtitle = df.formatCellValue(new1);

                new1 = workbook.getSheetAt(0).getRow(i).getCell(6);
                newUnit.leaderSkill = df.formatCellValue(new1);

                new1 = workbook.getSheetAt(0).getRow(i).getCell(7);
                newUnit.passive1 = df.formatCellValue(new1);

                new1 = workbook.getSheetAt(0).getRow(i).getCell(8);
                newUnit.passive60 = df.formatCellValue(new1);

                new1 = workbook.getSheetAt(0).getRow(i).getCell(9);
                newUnit.skill1 = df.formatCellValue(new1);

                new1 = workbook.getSheetAt(0).getRow(i).getCell(10);
                newUnit.skill2desc = df.formatCellValue(new1);

                new1 = workbook.getSheetAt(0).getRow(i).getCell(11);
                newUnit.skill2CD = df.formatCellValue(new1);

                new1 = workbook.getSheetAt(0).getRow(i).getCell(12);
                newUnit.skill3desc = df.formatCellValue(new1);

                new1 = workbook.getSheetAt(0).getRow(i).getCell(13);
                newUnit.skill3CD = df.formatCellValue(new1);

                myHeroBase.put(newUnit.name.toLowerCase(), newUnit);
            }
        }
        catch (Exception e) {
        }
    }

    public Main(){
    }

    public Unit getUnitByName(String name){
        return myHeroBase.get(name);
    }

    static int tapNum = 0;
    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "xxxxx";
        builder.setToken(token);
        builder.addEventListener(new Main());
        builder.buildAsync();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        String rawMessage = event.getMessage().getContentRaw();
        String lowerMessage = event.getMessage().getContentRaw().toLowerCase();

        if(rawMessage.equals("!kc tap")){
            tapNum++;
            event.getChannel().sendMessage("I have received " + tapNum + " taps.").queue();
        }
        else if(lowerMessage.startsWith("!kc unit ")) {
            String unitName = lowerMessage.substring(9);
            Main callName = new Main();
            Unit callUnit = callName.getUnitByName(unitName);
            if(callUnit != null){
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("***"+callUnit.name+"***, *\"" + callUnit.subtitle + "\"*" + System.lineSeparator() + "__" + callUnit.category + "__ | Rarity: " + callUnit.rarity + " | Element: " + callUnit.element, null);
                if(callUnit.element.equals("Fire")){
                    eb.setColor(new Color(251, 51, 51));
                }
                else if(callUnit.element.equals("Water")){
                    eb.setColor(new Color(94, 183,242));
                }
                else if(callUnit.element.equals("Wind")){
                    eb.setColor(new Color(93,221,123));
                }
                else if(callUnit.element.equals("Light")){
                    eb.setColor(new Color(255,252,122));
                }
                else if(callUnit.element.equals("Dark")){
                    eb.setColor(new Color(184,137,237));
                }
                else if(callUnit.element.equals("Varies")){
                    eb.setColor(new Color(216,216,216));
                }
                if(callUnit.leaderSkill.equals("None.")){
                    eb.addField("**Leader Skill:**", (System.lineSeparator() + "None."), false);
                    eb.addField("**Passive (1):**", (System.lineSeparator() + "None."), false);
                    eb.addField("**Passive(60):**", (System.lineSeparator() + "None."), false);
                    eb.addField("**Skill 1:**", (System.lineSeparator() + "**" + callUnit.skill1.substring(0, callUnit.skill1.indexOf(':'))
                            + "** " + callUnit.skill1.substring(callUnit.skill1.indexOf(':'))), false);
                    eb.addField("**Skill 2:**", (System.lineSeparator() + "**" + callUnit.skill2desc.substring(0, callUnit.skill2desc.indexOf(':'))
                            + "** " + callUnit.skill2desc.substring(callUnit.skill2desc.indexOf(':')) + System.lineSeparator() + "*Cooldown*: " + callUnit.skill2CD), false);
                    eb.addField("**Skill 3:**", (System.lineSeparator() + "None."), false);
                }
                else{
                    eb.addField("**Leader Skill:**", ("**" + callUnit.leaderSkill.substring(0, callUnit.leaderSkill.indexOf(':')) + "**" + callUnit.leaderSkill.substring(callUnit.leaderSkill.indexOf(':'))), false);
                    eb.addField("**Passive (1):**", ("**" + callUnit.passive1.substring(0, callUnit.passive1.indexOf(':')) + "**" + callUnit.passive1.substring(callUnit.passive1.indexOf(':'))), false);
                    eb.addField("**Passive (60):**", ("**" + callUnit.passive60.substring(0, callUnit.passive60.indexOf(':')) + "**" + callUnit.passive60.substring(callUnit.passive60.indexOf(':'))), false);
                    eb.addField("**Skill 1:**", (System.lineSeparator() + "**" + callUnit.skill1.substring(0, callUnit.skill1.indexOf(':'))
                            + "**" + callUnit.skill1.substring(callUnit.skill1.indexOf(':'))), false);
                    eb.addField("**Skill 2:**", (System.lineSeparator() + "**" + callUnit.skill2desc.substring(0, callUnit.skill2desc.indexOf(':'))
                            + "**" + callUnit.skill2desc.substring(callUnit.skill2desc.indexOf(':')) + System.lineSeparator() + "*Cooldown*: " + callUnit.skill2CD), false);
                    eb.addField("**Skill 3:**", (System.lineSeparator() + "**" + callUnit.skill3desc.substring(0, callUnit.skill3desc.indexOf(':'))
                            + "**" + callUnit.skill3desc.substring(callUnit.skill3desc.indexOf(':')) + System.lineSeparator() + "*Cooldown*: " + callUnit.skill3CD), false);
                }
                event.getChannel().sendMessage(eb.build()).queue();
            }
            else{
                event.getChannel().sendMessage("The unit \"" + rawMessage.substring(9) + "\" is not a part of the Unit Database."
                        + System.lineSeparator()+ System.lineSeparator() + "*if you believe this is a mistake, please DM ivaldi#1726*"
                        ).queue();
            }
        }
        else if(lowerMessage.equals("!kc help")){
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(new Color(255, 255, 255));
            eb.setTitle("**Commands**:");
            eb.addField("!kc unit *unitName*", "Calls the rarity, passives, and skills of unitName." + System.lineSeparator() + "Example: `!kc unit mary`", false);
            event.getChannel().sendMessage(eb.build()).queue();

        }

    }
}
