package moze_intel.projecte.events;

import com.google.common.math.LongMath;
import moze_intel.projecte.api.item.IItemEmc;
import moze_intel.projecte.api.item.IPedestalItem;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import moze_intel.projecte.config.ProjectEConfig;
import moze_intel.projecte.gameObjs.ObjHandler;
import moze_intel.projecte.gameObjs.gui.GUIPedestal;
import moze_intel.projecte.utils.Constants;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

@SideOnly(Side.CLIENT)
public class ToolTipEvent 
{
	@SubscribeEvent
	public void tTipEvent(ItemTooltipEvent event)
	{
		ItemStack current = event.itemStack;
		Item currentItem = current.getItem();
		Block currentBlock = Block.getBlockFromItem(currentItem);

		if (current == null)
		{
			return;
		}

		if (currentBlock == ObjHandler.dmPedestal)
		{
			event.toolTip.add(StatCollector.translateToLocal("pe.pedestal.tooltip1"));
			event.toolTip.add(StatCollector.translateToLocal("pe.pedestal.tooltip2"));
		}

		if (currentItem == ObjHandler.manual)
		{
			event.toolTip.add(StatCollector.translateToLocal("pe.manual.tooltip1"));
		}

		if (ProjectEConfig.showPedestalTooltip
			&& currentItem instanceof IPedestalItem)
		{
			if (ProjectEConfig.showPedestalTooltipInGUI)
			{
				if (Minecraft.getMinecraft().currentScreen instanceof GUIPedestal)
				{
					event.toolTip.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("pe.pedestal.on_pedestal") + " ");
					List<String> description = ((IPedestalItem) currentItem).getPedestalDescription();
					if (description.isEmpty())
					{
						event.toolTip.add(IPedestalItem.TOOLTIPDISABLED);
					}
					else
					{
						event.toolTip.addAll(((IPedestalItem) currentItem).getPedestalDescription());
					}
				}
			}
			else
			{
				event.toolTip.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("pe.pedestal.on_pedestal") + " ");
				List<String> description = ((IPedestalItem) currentItem).getPedestalDescription();
				if (description.isEmpty())
				{
					event.toolTip.add(IPedestalItem.TOOLTIPDISABLED);
				}
				else
				{
					event.toolTip.addAll(((IPedestalItem) currentItem).getPedestalDescription());
				}
			}
			
		}

		if (ProjectEConfig.showUnlocalizedNames)
		{
			event.toolTip.add("UN: " + Item.itemRegistry.getNameForObject(current.getItem()));
		}
		
		if (ProjectEConfig.showODNames)
		{
			for (int id : OreDictionary.getOreIDs(current))
			{
				event.toolTip.add("OD: " + OreDictionary.getOreName(id));
			}
		}

		if (ProjectEConfig.showEMCTooltip)
		{
			if (EMCHelper.doesItemHaveEmc(current))
			{
				int value = EMCHelper.getEmcValue(current);

				event.toolTip.add(EnumChatFormatting.YELLOW +
						StatCollector.translateToLocal("pe.emc.emc_tooltip_prefix") + " " + EnumChatFormatting.WHITE + String.format("%,d", value));

				if (current.stackSize > 1)
				{
					long total;
					try
					{
						total = LongMath.checkedMultiply(value, current.stackSize);
					} catch (ArithmeticException e) {
						total = Long.MAX_VALUE;
					}
					if (total < 0 || total <= value || total > Integer.MAX_VALUE)
					{
						event.toolTip.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal("pe.emc.stackemc_tooltip_prefix") + " " + EnumChatFormatting.OBFUSCATED + StatCollector.translateToLocal("pe.emc.too_much"));
					}
					else
					{
						event.toolTip.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal("pe.emc.stackemc_tooltip_prefix") + " " + EnumChatFormatting.WHITE + String.format("%,d", value * current.stackSize));
					}

				}
			}
		}

		if (ProjectEConfig.showStatTooltip)
		{
			/**
			 * Collector ToolTips
			 */
			String unit = StatCollector.translateToLocal("pe.emc.name");
			String rate = StatCollector.translateToLocal("pe.emc.rate");

			if (currentBlock == ObjHandler.energyCollector)
			{
				event.toolTip.add(EnumChatFormatting.DARK_PURPLE
						+ String.format(StatCollector.translateToLocal("pe.emc.maxgenrate_tooltip")
						+ EnumChatFormatting.BLUE + " %d " + rate, Constants.COLLECTOR_MK1_GEN));
				event.toolTip.add(EnumChatFormatting.DARK_PURPLE
						+ String.format(StatCollector.translateToLocal("pe.emc.maxstorage_tooltip")
						+ EnumChatFormatting.BLUE + " %d " + unit, Constants.COLLECTOR_MK1_MAX));
			}

			if (currentBlock == ObjHandler.collectorMK2)
			{
				event.toolTip.add(EnumChatFormatting.DARK_PURPLE
						+ String.format(StatCollector.translateToLocal("pe.emc.maxgenrate_tooltip")
						+ EnumChatFormatting.BLUE + " %d " + rate, Constants.COLLECTOR_MK2_GEN));
				event.toolTip.add(EnumChatFormatting.DARK_PURPLE
						+ String.format(StatCollector.translateToLocal("pe.emc.maxstorage_tooltip")
						+ EnumChatFormatting.BLUE + " %d " + unit, Constants.COLLECTOR_MK2_MAX));
			}

			if (currentBlock == ObjHandler.collectorMK3)
			{
				event.toolTip.add(EnumChatFormatting.DARK_PURPLE
						+ String.format(StatCollector.translateToLocal("pe.emc.maxgenrate_tooltip")
						+ EnumChatFormatting.BLUE + " %d " + rate, Constants.COLLECTOR_MK3_GEN));
				event.toolTip.add(EnumChatFormatting.DARK_PURPLE
						+ String.format(StatCollector.translateToLocal("pe.emc.maxstorage_tooltip")
						+ EnumChatFormatting.BLUE + " %d " + unit, Constants.COLLECTOR_MK3_MAX));
			}

			/**
			 * Relay ToolTips
			 */
			if (currentBlock == ObjHandler.relay)
			{
				event.toolTip.add(EnumChatFormatting.DARK_PURPLE
						+ String.format(StatCollector.translateToLocal("pe.emc.maxoutrate_tooltip")
						+ EnumChatFormatting.BLUE + " %d " + rate, Constants.RELAY_MK1_OUTPUT));
				event.toolTip.add(EnumChatFormatting.DARK_PURPLE
						+ String.format(StatCollector.translateToLocal("pe.emc.maxstorage_tooltip")
						+ EnumChatFormatting.BLUE + " %d " + unit, Constants.RELAY_MK1_MAX));
			}

			if (currentBlock == ObjHandler.relayMK2)
			{
				event.toolTip.add(EnumChatFormatting.DARK_PURPLE
						+ String.format(StatCollector.translateToLocal("pe.emc.maxoutrate_tooltip")
						+ EnumChatFormatting.BLUE + " %d " + rate, Constants.RELAY_MK2_OUTPUT));
				event.toolTip.add(EnumChatFormatting.DARK_PURPLE
						+ String.format(StatCollector.translateToLocal("pe.emc.maxstorage_tooltip")
						+ EnumChatFormatting.BLUE + " %d " + unit, Constants.RELAY_MK2_MAX));
			}

			if (currentBlock == ObjHandler.relayMK3)
			{
				event.toolTip.add(EnumChatFormatting.DARK_PURPLE
						+ String.format(StatCollector.translateToLocal("pe.emc.maxoutrate_tooltip")
						+ EnumChatFormatting.BLUE + " %d " + rate, Constants.RELAY_MK3_OUTPUT));
				event.toolTip.add(EnumChatFormatting.DARK_PURPLE
						+ String.format(StatCollector.translateToLocal("pe.emc.maxstorage_tooltip")
						+ EnumChatFormatting.BLUE + " %d " + unit, Constants.RELAY_MK3_MAX));
			}
		}

		if (current.hasTagCompound())
		{
			if (current.getTagCompound().getBoolean("ProjectEBlock"))
			{
				event.toolTip.add(EnumChatFormatting.GREEN + StatCollector.translateToLocal("pe.misc.wrenched_block"));
				
				if (current.getTagCompound().getDouble("EMC") > 0)
				{
					event.toolTip.add(EnumChatFormatting.YELLOW + String.format(
							StatCollector.translateToLocal("pe.emc.storedemc_tooltip") + " " + EnumChatFormatting.RESET + "%,d", (int) current.getTagCompound().getDouble("EMC")));
				}
			}

			if (current.getItem() instanceof IItemEmc || current.getTagCompound().hasKey("StoredEMC"))
			{
				double value = 0;
				if (current.getTagCompound().hasKey("StoredEMC"))
				{
					value = current.getTagCompound().getDouble("StoredEMC");
				} else
				{
					value = ((IItemEmc) current.getItem()).getStoredEmc(current);
				}

				event.toolTip.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal("pe.emc.storedemc_tooltip") + " " + EnumChatFormatting.RESET + Constants.EMC_FORMATTER.format(value));
			}

			if (current.getTagCompound().hasKey("StoredXP"))
			{
				event.toolTip.add(String.format(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocal("pe.misc.storedxp_tooltip") + " " + EnumChatFormatting.GREEN + "%,d", current.getTagCompound().getInteger("StoredXP")));
			}
		}
	}
}
