package shadowfox.botanicaladdons.common.block.colored

import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyInteger
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.color.IBlockColor
import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import shadowfox.botanicaladdons.client.core.ModelHandler
import shadowfox.botanicaladdons.common.block.base.BlockMod
import shadowfox.botanicaladdons.common.lexicon.LexiconEntries
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.awt.Color

/**
 * @author WireSegal
 * Created at 12:57 PM on 5/31/16.
 */
class BlockColoredLamp(name: String) : BlockMod(name, Material.REDSTONE_LIGHT), ModelHandler.IBlockColorProvider, ILexiconable {

    companion object {
        val POWER = PropertyInteger.create("power", 0, 15)

        fun powerColor(power: Int): Int {
            if (power == 0) return 0x191616
            else if (power == 15) return 0xFFFFFF
            return Color.HSBtoRGB((power - 1) / 16F, 1F, 1F)
        }

        fun blockPower(world: World, pos: BlockPos): Int {
            var redstone = 0
            for (facing in EnumFacing.VALUES) {
                redstone = Math.max(redstone, world.getRedstonePower(pos.offset(facing), facing))
            }
            return redstone
        }


    }

    override fun getLightValue(state: IBlockState, world: IBlockAccess, pos: BlockPos): Int {
        return if (state.getValue(POWER) > 0) 15 else 0
    }

    @SideOnly(Side.CLIENT)
    override fun getColor(): IItemColor? {
        return IItemColor { itemStack, i -> powerColor(0) }
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockColor(): IBlockColor? {
        return IBlockColor { iBlockState, iBlockAccess, blockPos, i -> powerColor(iBlockState.getValue(POWER)) }
    }

    init {
        soundType = SoundType.GLASS
        blockHardness = 0.3f
    }

    override fun createBlockState(): BlockStateContainer? {
        return BlockStateContainer(this, POWER)
    }

    override fun getMetaFromState(state: IBlockState): Int {
        return state.getValue(POWER)
    }

    override fun getStateFromMeta(meta: Int): IBlockState? {
        return defaultState.withProperty(POWER, meta)
    }

    override fun neighborChanged(state: IBlockState, world: World, pos: BlockPos, blockIn: Block) {
        val power = blockPower(world, pos)
        if (state.getValue(POWER) != power)
            world.setBlockState(pos, state.withProperty(POWER, power), 2)
    }

    override fun onBlockAdded(world: World, pos: BlockPos, state: IBlockState) {
        val power = blockPower(world, pos)
        if (state.getValue(POWER) != power)
            world.setBlockState(pos, state.withProperty(POWER, power), 2)
    }

    override fun getEntry(p0: World?, p1: BlockPos?, p2: EntityPlayer?, p3: ItemStack?): LexiconEntry? {
        return LexiconEntries.lamp
    }

}