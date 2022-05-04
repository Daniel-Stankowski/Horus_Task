import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Wall implements Structure {
    private List<Block> blocks;
    
    @Override
    public Optional<Block> findBlockByColor(String color) {
        for( Block block : blocks){
            if(block instanceof CompositeBlock){
                Optional<Block> optionalBlock = findBlockByColorComposite((CompositeBlock)block,color);
                if(optionalBlock.isPresent()){
                    return optionalBlock;
                }
            } else {
                if(checkStringEquality(color,block.getColor())){
                    return Optional.of(block);
                }
            }
        }
        return Optional.empty();
    }
    private Optional<Block> findBlockByColorComposite(CompositeBlock compositeBlock , String color){
        for ( Block block : compositeBlock.getBlocks()){
            if(block instanceof CompositeBlock){
                Optional<Block> optionalBlock = findBlockByColorComposite((CompositeBlock)block,color);
                if(optionalBlock.isPresent()){
                    return optionalBlock;
                }
            } else {
                if(checkStringEquality(color,block.getColor())){
                    return Optional.of(block);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Block> findBlockByMaterial(String material) {
        List<Block> blocksOfSpecificMaterial = new ArrayList<>();
        for( Block block : blocks){
            if(block instanceof CompositeBlock){
                for ( Block composedBlock : ((CompositeBlock)block).getBlocks()){
                    blocksOfSpecificMaterial.addAll(findBlockByMaterialComposite(
                        (CompositeBlock)composedBlock,material));
                }
            } else {
                if(checkStringEquality(block.getMaterial(), material)){
                    blocksOfSpecificMaterial.add(block);
                }
            }
        }
        return blocksOfSpecificMaterial;
    }
    private List<Block> findBlockByMaterialComposite(CompositeBlock compositeBlock, String material){
        List<Block> blocksOfSpecificMaterial = new ArrayList<>();
        for ( Block block : compositeBlock.getBlocks()){
            if(block instanceof CompositeBlock){
                for ( Block composedBlock : ((CompositeBlock)block).getBlocks()){
                    blocksOfSpecificMaterial.addAll(findBlockByMaterialComposite(
                        (CompositeBlock)composedBlock,material));
                }
            } else {
                if(checkStringEquality(block.getMaterial(), material)){
                    blocksOfSpecificMaterial.add(block);
                }
            }
        }
        return blocksOfSpecificMaterial;
    }

    private boolean checkStringEquality(String s1, String s2) {
        return s1.equals(s2);
    }

    @Override
    public int count() {
        int counter = 0;
        for( Block block : blocks){
            counter += block instanceof CompositeBlock ? countComposite((CompositeBlock)block) : 1;
        }
        return counter;
    }
    private int countComposite(CompositeBlock compositeBlock){
        int counter=0;
        for(Block block : compositeBlock.getBlocks()){
            counter += block instanceof CompositeBlock ? countComposite((CompositeBlock)block) : 1;
        }
        return counter;
    }
}
