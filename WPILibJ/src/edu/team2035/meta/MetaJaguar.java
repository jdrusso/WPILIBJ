package edu.team2035.meta;
import edu.wpi.first.wpilibj.Jaguar;
public class MetaJaguar extends Jaguar implements MetaObject {
    public MetaJaguar (int channel){
        super(channel);
        Meta.addObject(this);
    }
}