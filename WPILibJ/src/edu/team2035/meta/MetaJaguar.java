/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST Team 2035, 2012. All Rights Reserved.                  */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.team2035.meta;

import edu.wpi.first.wpilibj.Jaguar;

/**
 * 
 * @author Team 2035 Programmers
 */
public class MetaJaguar extends Jaguar implements MetaObject {
    public MetaJaguar (int channel){
        super(channel);
        Meta.addObject(this);
    }
}