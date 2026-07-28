package fr.lifesteal.respectguard.business;

import fr.lifesteal.respectguard.business.Interface.IChatGptService;
import fr.lifesteal.respectguard.business.Interface.IChatGuardService;
import fr.lifesteal.respectguard.business.config.Interface.IConfigurationService;
import fr.lifesteal.respectguard.business.wrapper.Interface.ICommandDispatcherWrapper;
import fr.lifesteal.respectguard.business.wrapper.Interface.IEventCallerWrapper;
import fr.lifesteal.respectguard.event.BadMessageEvent;
import org.bukkit.entity.Player;

public class ChatGuardService implements IChatGuardService {

    private final IChatGptService chatGptService;
    private final IConfigurationService configurationService;
    private final IEventCallerWrapper eventCaller;

    public ChatGuardService(IChatGptService chatGptService, IConfigurationService configurationService, IEventCallerWrapper eventCaller) {
        this.chatGptService = chatGptService;
        this.configurationService = configurationService;
        this.eventCaller = eventCaller;
    }

    @Override
    public boolean analyzePlayerMessage(Player player, String message) {
        var analysesResult = this.chatGptService.analyzeMessage(message);

        if (!analysesResult.isHarmful()) return false;

        this.eventCaller.callEvent(new BadMessageEvent(player, message, true, analysesResult.getHarmfulCategories()));

        return this.configurationService.hasEventToBeCancel();
    }
}
