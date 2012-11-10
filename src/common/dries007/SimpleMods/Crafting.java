package dries007.SimpleMods;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.*;

public class Crafting extends ContainerWorkbench
{

	public Crafting(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5) 
	{
		super(par1InventoryPlayer, par2World, par3, par4, par5);
	}
	
	 public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	 {
		 return true;
	 }

}
