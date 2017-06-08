package shadowfox.botanicaladdons.common.block.colored

import com.teamwizardry.librarianlib.core.LibrarianLib
import com.teamwizardry.librarianlib.features.base.block.BlockMod
import com.teamwizardry.librarianlib.features.base.block.BlockModLeaves
import com.teamwizardry.librarianlib.features.base.block.IBlockColorProvider
import com.teamwizardry.librarianlib.features.utilities.client.TooltipHelper.addToTooltip
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.ColorizerFoliage
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.IPlantable
import shadowfox.botanicaladdons.api.lib.LibMisc
import shadowfox.botanicaladdons.common.lexicon.LexiconEntries
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import net.minecraft.world.biome.BiomeColorHelper
import shadowfox.botanicaladdons.common.block.ModBlocks
import java.util.*
import kotlin.coroutines.experimental.EmptyCoroutineContext.plus


/**
 * @author WireSegal
 * Created at 10:38 PM on 5/6/16.
 */
class BlockAuroraLeaves(name: String) : BlockModLeaves(name), ILexiconable, IBlockColorProvider {

    override val canBeOpaque: Boolean
        get() = false

    override fun getItemDropped(state: IBlockState, rand: Random, fortune: Int): Item? {
        return ModBlocks.irisSapling.itemForm
    }

    override fun getEntry(p0: World?, p1: BlockPos?, p2: EntityPlayer?, p3: ItemStack): LexiconEntry? {
        return LexiconEntries.aurora
    }

    override fun addInformation(stack: ItemStack, player: EntityPlayer?, tooltip: MutableList<String>, advanced: Boolean) {
        addToTooltip(tooltip, "misc.${LibMisc.MOD_ID}.color.aurora")
    }

    override val blockColorFunction: ((state: IBlockState, world: IBlockAccess?, pos: BlockPos?, tintIndex: Int) -> Int)?
        get() = { _, _, pos, _ -> BlockAuroraDirt.fromPos(pos) }

    override val itemColorFunction: ((ItemStack, Int) -> Int)?
        get() = { _, _ -> BlockAuroraDirt.fromPos(LibrarianLib.PROXY.getClientPlayer().position) }
}
