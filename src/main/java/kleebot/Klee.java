package kleebot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Klee {

    public static JDABuilder builder;
    public static String prefix;

    public static void main(String[] args) throws LoginException {
        String token = System.getenv("DISCORD_TOKEN");
        prefix = "k!";

        builder = JDABuilder.createDefault(token);

        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.CLIENT_STATUS, CacheFlag.ACTIVITY, CacheFlag.EMOTE);

        builder.setBulkDeleteSplittingEnabled(false);

        builder.setCompression(Compression.NONE);

        builder.setActivity(Activity.playing("with Dodoko! (Beta Version: 0.2 | " + prefix + "readme)"));

        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES);

        builder.setMemberCachePolicy(MemberCachePolicy.ALL);

        //SongCaller.initSongCaller();

        KleeHandler.addKleeCommands(builder);

        //loadMembers();
        builder.build();
    }
}
