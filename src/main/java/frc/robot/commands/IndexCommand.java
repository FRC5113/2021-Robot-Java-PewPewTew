package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HopUp;
import frc.robot.subsystems.Indexer;


public class IndexCommand extends CommandBase {

    Indexer indexer;
    HopUp hopper;

    public IndexCommand(Indexer indexer, HopUp hopper) {
        addRequirements(indexer, hopper);
        this.indexer = indexer;
        this.hopper = hopper;

    }

    @Override
    public void execute() {
        indexer.setSpeed();
    }

    @Override
    public void end(boolean interuppted) {
        indexer.stopIndexing();
    }

}