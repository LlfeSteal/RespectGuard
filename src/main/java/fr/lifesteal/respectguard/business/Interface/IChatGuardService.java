package fr.lifesteal.respectguard.business.Interface;

import org.bukkit.entity.Player;

public interface IChatGuardService {
    boolean analyzePlayerMessage(Player player, String message);
}
