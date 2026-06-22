package com.prayermetronome;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

import net.runelite.api.SoundEffectVolume;

@ConfigGroup("prayer-metronome")
public interface PrayerMetronomeConfig extends Config
{

	int VOLUME_MAX = SoundEffectVolume.HIGH;

	@ConfigItem(
		keyName = "volume",
		name = "Volume",
		description = "Configures the volume of the tick sound. A value of 0 will disable tick sounds.",
		position = 1
	)
	@Range(max = VOLUME_MAX)
	default int volume()
	{
		return SoundEffectVolume.MEDIUM_HIGH;
	}

	@ConfigItem(
		keyName = "soundOnNoPrayer",
		name = "Play sound on no prayer",
		description = "Play a sound on ticks when no protection prayer is active",
		position = 2
	)
	default boolean soundOnNoPrayer()
	{
		return true;
	}

	@ConfigItem(
		keyName = "muteActivationSounds",
		name = "Mute original prot. prayer sounds",
		description = "Mute the default game sounds played when activating protection prayers",
		position = 3
	)
	default boolean muteActivationSounds()
	{
		return false;
	}
}
