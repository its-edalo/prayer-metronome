package com.prayermetronome;

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Preferences;
import net.runelite.api.Prayer;
import net.runelite.api.SoundEffectID;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.SoundEffectPlayed;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
	name = "Prayer Metronome",
	description = "Plays a sound on each game tick",
	tags = {"prayer", "metronome", "tick", "sound"}
)
public class PrayerMetronomePlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private PrayerMetronomeConfig config;

	@Provides
	PrayerMetronomeConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PrayerMetronomeConfig.class);
	}

	private static final int defaultTickSound = SoundEffectID.GE_INCREMENT_PLOP;

	@Subscribe
	public void onSoundEffectPlayed(SoundEffectPlayed soundEffectPlayed)
	{
		if (!config.muteActivationSounds())
		{
			return;
		}

		int soundId = soundEffectPlayed.getSoundId();
		if (soundId == SoundEffectID.PRAYER_ACTIVATE_PROTECT_FROM_MAGIC
			|| soundId == SoundEffectID.PRAYER_ACTIVATE_PROTECT_FROM_MISSILES
			|| soundId == SoundEffectID.PRAYER_ACTIVATE_PROTECT_FROM_MELEE)
		{
			soundEffectPlayed.consume();
		}
	}

	private Integer getTickSound()
	{
		if (client.isPrayerActive(Prayer.PROTECT_FROM_MAGIC)) {
			return 174; // magic_dart_hit
		}

		if (client.isPrayerActive(Prayer.PROTECT_FROM_MISSILES)) {
			return 652; // barklike
		}

		if (client.isPrayerActive(Prayer.PROTECT_FROM_MELEE)) {
			return 4198; // giant_basilisk_stomp
		}

		return defaultTickSound;
	}

	private int getTickVolume()
	{
		if (client.isPrayerActive(Prayer.PROTECT_FROM_MAGIC)
			|| client.isPrayerActive(Prayer.PROTECT_FROM_MISSILES)
			|| client.isPrayerActive(Prayer.PROTECT_FROM_MELEE))
		{
			return config.prayerVolume();
		}

		return config.noPrayerVolume();
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		int tickVolume = getTickVolume();

		if (tickVolume > 0)
		{
			Integer tickSound = getTickSound();

			Preferences preferences = client.getPreferences();
			int previousVolume = preferences.getSoundEffectVolume();

			preferences.setSoundEffectVolume(tickVolume);

			client.playSoundEffect(tickSound, tickVolume);

			preferences.setSoundEffectVolume(previousVolume);
		}
	}
}
