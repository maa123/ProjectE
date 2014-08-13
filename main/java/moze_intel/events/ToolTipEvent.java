package moze_intel.events;

import moze_intel.EMC.EMCMapper;
import moze_intel.EMC.IStack;
import moze_intel.utils.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ToolTipEvent 
{
	@SubscribeEvent
	public void tTipEvent(ItemTooltipEvent event)
	{
		ItemStack current = event.itemStack;
		
		if (current == null)
		{
			return;
		}
		
		/*for (int id : OreDictionary.getOreIDs(current))
		{
			event.toolTip.add(OreDictionary.getOreName(id));
		}*/
		
		IStack stack = new IStack(current);
		
		if (EMCMapper.emc.containsKey(stack))
		{
			int value = EMCMapper.emc.get(stack);
			event.toolTip.add(String.format("EMC: %,d", value));
			
			if (current.stackSize > 1)
			{
				event.toolTip.add(String.format("Stack EMC: %,d", value * current.stackSize));
			}
		}
		
		if (current.hasTagCompound())
		{
			if (current.stackTagCompound.hasKey("StoredEMC"))
			{
				event.toolTip.add(String.format("Stored EMC: %,d", (int) current.stackTagCompound.getDouble("StoredEMC")));
			}
			else if (current.stackTagCompound.hasKey("StoredXP"))
			{
				event.toolTip.add(String.format("Stored XP: %,d", current.stackTagCompound.getInteger("StoredXP")));
			}
		}
	}
}
