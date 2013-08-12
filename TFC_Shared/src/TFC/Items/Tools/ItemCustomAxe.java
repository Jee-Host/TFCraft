package TFC.Items.Tools;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import TFC.Reference;
import TFC.TerraFirmaCraft;
import TFC.API.ICausesDamage;
import TFC.API.ISize;
import TFC.API.TFCTabs;
import TFC.API.Enums.EnumDamageType;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_Settings;
import TFC.Core.Util.StringUtil;
import TFC.Items.ItemTerra;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ItemCustomAxe extends ItemAxe implements ISize, ICausesDamage
{	
	public ItemCustomAxe(int i, EnumToolMaterial e)
	{
		super(i, e);
		this.setMaxDamage(e.getMaxUses());
		this.damageVsEntity = 100 + e.getDamageVsEntity();
		setCreativeTab(TFCTabs.TFCTools);
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + "tools/"+this.getUnlocalizedName().replace("item.", ""));
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(this, arraylist);

		if(TFC_Settings.enableDebugMode) {
			arraylist.add("Damage: " + is.getItemDamage());
		}
	}

	@Override
	public int getItemStackLimit()
	{
		if(canStack()) {
			return this.getSize().stackSize * getWeight().multiplier;
		} else {
			return 1;
		}
	}

	@Override
	public EnumSize getSize() {
		return EnumSize.LARGE;
	}

	@Override
	public boolean canStack() 
	{
		return false;
	}

	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.MEDIUM;
	}

	@Override
	public EnumDamageType GetDamageType() {
		// TODO Auto-generated method stub
		return EnumDamageType.SLASHING;
	}

	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		return StringUtil.localize(getUnlocalizedName(itemstack).replace(" ", ""));
	}

	@Override
	public Multimap func_111205_h()
	{
		Multimap multimap = HashMultimap.create();
		if(!TerraFirmaCraft.proxy.isRemote() || TFC_Settings.enableDebugMode) 
		{
			multimap.put(SharedMonsterAttributes.field_111264_e.func_111108_a(), new AttributeModifier(field_111210_e, "Tool modifier", this.damageVsEntity, 0));
		}

		return multimap;
	}
}