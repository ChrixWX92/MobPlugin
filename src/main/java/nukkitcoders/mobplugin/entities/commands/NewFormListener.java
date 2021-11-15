package nukkitcoders.mobplugin.entities.commands;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.utils.TextFormat;
import com.denzelcode.form.FormAPI;
import com.denzelcode.form.element.Button;
import com.denzelcode.form.element.Input;
import com.denzelcode.form.event.CustomFormSubmitEvent;
import com.denzelcode.form.event.ModalFormSubmitEvent;
import com.denzelcode.form.event.SimpleFormButtonClickEvent;
import com.denzelcode.form.window.CustomWindowForm;
import com.denzelcode.form.window.ModalWindowForm;
import com.denzelcode.form.window.SimpleWindowForm;
import com.gms.mc.error.InvalidFrameWriteException;

public class NewFormListener implements Listener {

    public static String petName;

    @EventHandler(priority = EventPriority.NORMAL)
    public void onIndivPetSubmit(ModalFormSubmitEvent event) {

        ModalWindowForm form = event.getForm();
        Player p = event.getPlayer();

        if (!event.isFormValid("indivPet")) return;
        //if (form.wasClosed()) return;

        if (event.isAccepted()) {

                FormAPI.customWindowForm("petName","§l§n§1 Name your " + TextFormat.DARK_PURPLE + Pets.getChosenPet() +"§l§n§1?")
                        .addLabel("If you'd like to give your new " + Pets.getChosenPet() + " a name, you can enter it below!")
                        .addInput("name", "")
                        .sendTo(p);
            }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPetNameSubmit(CustomFormSubmitEvent event) throws InvalidFrameWriteException {

        CustomWindowForm form = event.getForm();
        Player p = event.getPlayer();

        if (!event.isFormValid("petName")) return;
        if (form.wasClosed()) return;

        Input name = form.getElement("name");

        if (!name.getValue().replace(" ", "").equals("")) {
            Pets.spawnHandler(Pets.getChosenPet(), event.getPlayer(), name.getValue());
        } else {
            Pets.spawnHandler(Pets.getChosenPet(), event.getPlayer(), "");
            petName = null;
        }

    }

   /*
    @EventHandler(priority = EventPriority.NORMAL)
       public void onLoginFormSubmit(CustomFormSubmitEvent event) {
        CustomWindowForm form = event.getForm();
        Player player = event.getPlayer();

        if (!event.isFormValid("login")) return;

        Input username = form.getElement("username");
        Input password = form.getElement("password");

        player.sendMessage("Player: " + player.getName());
        player.sendMessage("Form: " + form.getName());
        player.sendMessage("Username: " + username.getValue());
        player.sendMessage("Password: " + password.getValue());

        FormAPI.modalWindowForm(
                "login_remember",
                "Remember",
                "Do you want to remember your account in this device?",
                "Yes",
                "No"
        ).sendTo(player);
    }

   @EventHandler(priority = EventPriority.NORMAL)
    public void onRememberFormSubmit(ModalFormSubmitEvent event) {
        ModalWindowForm form = event.getForm();
        Player player = event.getPlayer();

        if (!event.isFormValid("login_remember")) return;

        boolean accepted = event.isAccepted();

        player.sendMessage("Player: " + player.getName());
        player.sendMessage("Form: " + form.getName());
        player.sendMessage("Accepted: " + (accepted ? "Yes" : "No"));

        FormAPI.simpleWindowForm("minigames", "Minigames", "Select a minigame which you want to play!")
                .addButton("skywars", "SkyWars")
                .addButton("luckyislands", "LuckyIslands")
                .sendTo(player);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onMinigameFormSubmit(SimpleFormButtonClickEvent event) {
        SimpleWindowForm form = event.getForm();
        Player player = event.getPlayer();
        Button button = event.getButton();

        if (!event.isFormValid("minigames")) return;

        player.sendMessage("Player: " + player.getName());
        player.sendMessage("Form: " + form.getName());
        player.sendMessage("Clicked button: " + button.getName());

        player.sendMessage("Successfully joined Minigame: " + button.getText() + "!");
    } */
}
