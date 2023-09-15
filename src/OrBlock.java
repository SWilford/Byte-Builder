/*
This is a test custom block that in reality would be made in the game.
The calculation for the output of the OR function utilizes OR and AND
in reality every custom object probably will not have its own class but have similar output as shown in the method
also wont be in one line since the method will build as the player builds in the program
 */
public class OrBlock extends Operator2I{
    public OrBlock(Operator n1, Operator n2){
        previous1 = n1;
        previous1.setNext(this);
        previous2 = n2;
        previous2.setNext(this);
    }
    public boolean getOutput(){
        return (new NotBlock(new AndBlock(new NotBlock(previous1), new NotBlock(previous2)))).getOutput();
    }
}
