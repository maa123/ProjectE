package moze_intel.gameObjs.container;

import moze_intel.gameObjs.tiles.DMFurnaceTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DMFurnaceContainer  extends Container
{
	private DMFurnaceTile tile;
	private int lastCookTime;
	private int lastBurnTime;
	private int lastItemBurnTime;
	
	public DMFurnaceContainer(InventoryPlayer invPlayer, DMFurnaceTile tile)	
	{
		this.tile = tile;
		
		//Fuel Slot
		this.addSlotToContainer(new Slot(tile, 0, 49, 53));
		
		//Input(0)
		this.addSlotToContainer(new Slot(tile, 1, 49, 17));
		
		//Input Storage
		for (int i = 0; i < 2; i++)
			for (int j  = 0; j < 4; j++)
				this.addSlotToContainer(new Slot(tile, i * 4 + j + 2, 13 + i * 18, 8 + j * 18));
		
		//Output
		this.addSlotToContainer(new Slot(tile, 10, 109, 35));
		
		//OutputStorage
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 4; j++)
				this.addSlotToContainer(new Slot(tile, i * 4 + j + 11, 131 + i * 18, 8 + j * 18));
		
		//Player Inventory
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
		
		//Player Hotbar
		for (int i = 0; i < 9; i++)
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) 
	{
		return true;
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, tile.furnaceCookTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, tile.furnaceBurnTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, tile.currentItemBurnTime);
    }
	
	@Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (lastCookTime != tile.furnaceCookTime)
                icrafting.sendProgressBarUpdate(this, 0, tile.furnaceCookTime);

            if (lastBurnTime != tile.furnaceBurnTime)
                icrafting.sendProgressBarUpdate(this, 1, tile.furnaceBurnTime);

            if (lastItemBurnTime != tile.currentItemBurnTime)
                icrafting.sendProgressBarUpdate(this, 2, tile.currentItemBurnTime);
        }

        lastCookTime = tile.furnaceCookTime;
        lastBurnTime = tile.furnaceBurnTime;
        lastItemBurnTime = tile.currentItemBurnTime;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
            tile.furnaceCookTime = par2;

        if (par1 == 1)
            tile.furnaceBurnTime = par2;

        if (par1 == 2)
            tile.currentItemBurnTime = par2;
    }
    
    @Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        return null;
    }
	
	/*@Override
	public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer)
	{
		if (!par4EntityPlayer.worldObj.isRemote)
			System.out.println(par1);
		return super.slotClick(par1, par2, par3, par4EntityPlayer);
	}*/
}
