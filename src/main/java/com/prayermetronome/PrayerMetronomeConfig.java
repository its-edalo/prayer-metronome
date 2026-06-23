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
		keyName = "prayerVolume",
		name = "Prayer tick volume",
		description = "Volume of the tick sound when a protection prayer is active.",
		position = 1
	)
	@Range(max = VOLUME_MAX)
	default int prayerVolume()
	{
		return SoundEffectVolume.MEDIUM_LOW;
	}

	@ConfigItem(
		keyName = "noPrayerVolume",
		name = "No prayer tick volume",
		description = "Volume of the tick sound when no protection prayer is active. A value of 0 will disable tick sounds without an active prayer.",
		position = 2
	)
	@Range(max = VOLUME_MAX)
	default int noPrayerVolume()
	{
		return SoundEffectVolume.MEDIUM_HIGH;
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
