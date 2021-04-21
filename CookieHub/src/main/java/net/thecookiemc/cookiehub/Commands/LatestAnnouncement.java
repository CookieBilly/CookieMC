package net.thecookiemc.cookiehub.Commands;

import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagString;
import net.minecraft.server.v1_8_R3.StatisticList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Core implements Listener {

  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    if (args.length > 0) {
      //Getting The Player
      Player p = Bukkit.getPlayer(args[0]);
      HumanEntity he = (HumanEntity) p;
      CraftHumanEntity che = (CraftHumanEntity) he;
      EntityHuman eh = che.getHandle();

      //Creating NMS ItemStack and a tag, setting some values.
      ItemStack book = new ItemStack(Item.getById(387));
      NBTTagCompound tag = new NBTTagCompound();
      tag.setString("author", "Notch");
      tag.setString("title", "My Dairy");
      tag.set("display", new NBTTagCompound());

      //Adding values to the "display" section (name and lore)
      NBTTagCompound display = tag.getCompound("display");
      display.setString("Name", ChatColor.translateAlternateColorCodes('&', "&cCustom Book GUI"));
      NBTTagList lore = new NBTTagList();
      lore.add(new NBTTagString("Hello There!"));
      lore.add(new NBTTagString("Why Am I adding a lore?"));
      lore.add(new NBTTagString("Nobody will see it!"));
      lore.add(new NBTTagString("Huh, well, it's added already..."));
      display.set("Lore", lore);

      //Now to set the pages
      NBTTagList pages = new NBTTagList();
      pages.add(new NBTTagString("How Are you? I'm the first page and I have nothing on special!"));

      //You can too add JSON!
      NBTTagCompound json = new NBTTagCompound();
      json.setString("text", "Click Me!");
      json.setString("color", "blue");
      json.setString("italic", "true");
      json.setString("underlined", "true");
      json.set("clickEvent", new NBTTagCompound());
      json.set("hoverEvent", new NBTTagCompound());
      //This includes Events
      NBTTagCompound clickEvent = json.getCompound("clickEvent");
      clickEvent.setString("action", "run_command");
      clickEvent.setString("value", "Vote Please!");
      //Hover Events are events too!
      NBTTagCompound hoverEvent = json.getCompound("hoverEvent");
      hoverEvent.setString("action", "show_text");
      hoverEvent.set("value", new NBTTagCompound());
      hoverEvent.setString("insertion", "what is this?");
      //You can have as many hierarchies as you like
      NBTTagCompound hoverValue = hoverEvent.getCompound("value");
      hoverValue.setString("text", "https://myWebsite.com/vote");
      hoverValue.setString("color", "aqua");
      hoverValue.setString("italic", "true");
      //Let's add this on a new page
      pages.add(new NBTTagString(json.toString()));

      //You can change through pages too
      NBTTagCompound returner = new NBTTagCompound();
      returner.setString("text", "Go to the start!");
      returner.set("clickEvent", new NBTTagCompound());
      NBTTagCompound rClickEvent = returner.getCompound("clickEvent");
      rClickEvent.setString("action", "change_page");
      rClickEvent.setInt("value", 1);
      pages.add(new NBTTagString(returner.toString()));

      //Add pages to the book
      tag.set("pages", pages);

      //Don't forget this
      book.setTag(tag);

      //This is IMPORTANT, remember to put the book on the player's hand
      org.bukkit.inventory.ItemStack hand = p.getItemInHand();
      p.setItemInHand(CraftItemStack.asBukkitCopy(book));

      //Opening the GUI
      eh.openBook(book);

      //Returning whatever was on hand.
      p.setItemInHand(hand);

      //Adding Statistics, as it was a normal book and quill.
      eh.b(StatisticList.USE_ITEM_COUNT[387]);
    }
    return true;
  }
}
