/*
This is a test custom block that in reality would be made in the game.
The calculation for the output of the OR function utilizes NOT and AND
in reality every custom object probably will not have its own class, but have similar output as shown in the method
also output won't all be in one line since the method will essentially build as the player builds in the program
 */
public class OrBlock extends Operator2I{
    public OrBlock(Operator n1, Operator n2){
        super(n1, n2);
    }
    public boolean getOutput(){
        return (new NotBlock(new AndBlock(new NotBlock(previous1), new NotBlock(previous2)))).getOutput();
    }
}
