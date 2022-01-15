/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 Team 501 - The PowerKnights. All Rights Reserved.       */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the 2020 Team 501 - The PowerKnights BSD license    */
/* file in the root directory of the project.                                 */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.wheel;

import frc.robot.subsystems.ISubsystem;
import frc.robot.utils.PKColor;

/**
 * Add your docs here.
 **/
public interface IWheelSubsystem extends ISubsystem {

    public void stop();

    public void runToColor(PKColor color);

    public void runRevolutions(double numRevolutions);

    public void runCounterClockwise();

    public void runCounterClockwise(double speed);

    public void runClockwise();

    public void runClockwise(double speed);

}
