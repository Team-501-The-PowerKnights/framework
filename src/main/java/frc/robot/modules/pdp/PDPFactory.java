/*-----------------------------------------------------------------------*/
/* Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/* Open Source Software - may be modified and shared by other FRC teams  */
/* under the terms of the Team501 license. The code must be accompanied  */
/* by the Team 501 - The PowerKnights license file in the root directory */
/* of this project.                                                      */
/*-----------------------------------------------------------------------*/

package frc.robot.modules.pdp;


import org.slf4j.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.modules.IModule;
import frc.robot.modules.ModuleNames;
import frc.robot.properties.PKProperties;
import frc.robot.properties.PropertiesManager;
import frc.robot.telemetry.TelemetryNames;
import frc.robot.utils.PKStatus;

import riolog.RioLogger;


/**
 * 
 */
public class PDPFactory {

    /** Our classes' logger **/
    private static final Logger logger = RioLogger.getLogger(PDPFactory.class.getName());

    /** Singleton instance of class for all to use **/
    private static IModule ourInstance;
    /** Name of our module **/
    private static final String myName = ModuleNames.pdpName;

    /**
     * Constructs instance of the module. Assumed to be called before any usage of
     * the module; and verifies only called once. Allows controlled startup
     * sequencing of the robot and all it's modules.
     **/
    public static synchronized void constructInstance() {
        SmartDashboard.putNumber(TelemetryNames.PDP.status, PKStatus.inProgress.tlmValue);

        if (ourInstance != null) {
            throw new IllegalStateException(myName + " Already Constructed");
        }

        PKProperties props = PropertiesManager.getInstance().getProperties(myName);
        props.listProperties();

        loadImplementationClass(props.getString("className"));
    }

    private static void loadImplementationClass(String myClassName) {
        String myPkgName = PDPFactory.class.getPackage().getName();
        String classToLoad = new StringBuilder().append(myPkgName).append(".").append(myClassName).toString();
        logger.debug("class to load: {}", classToLoad);

        logger.info("constructing {} for {} module", myClassName, myName);
        try {
            @SuppressWarnings("rawtypes")
            Class myClass = Class.forName(classToLoad);
            @SuppressWarnings("deprecation")
            Object myObject = myClass.newInstance();
            ourInstance = (IModule) myObject;
            SmartDashboard.putNumber(TelemetryNames.PDP.status, PKStatus.success.tlmValue);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            logger.error("failed to load class; instantiating default stub for: {}", myName);
            ourInstance = new StubPDPModule();
            SmartDashboard.putNumber(TelemetryNames.PDP.status, PKStatus.degraded.tlmValue);
        }
    }

    /**
     * Returns the singleton instance of the module in the form of the
     * <i>Interface</i> that is defined for it. If it hasn't been constructed yet,
     * throws an <code>IllegalStateException</code>.
     *
     * @return singleton instance of module
     **/
    public static IModule getInstance() {
        if (ourInstance == null) {
            throw new IllegalStateException(myName + " Not Constructed Yet");
        }

        return ourInstance;
    }

}
