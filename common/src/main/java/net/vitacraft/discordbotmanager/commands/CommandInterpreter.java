package net.vitacraft.discordbotmanager.commands;

import net.vitacraft.discordbotmanager.sandbox.Sandbox;
import net.vitacraft.discordbotmanager.sandbox.SandboxManager;
import net.vitacraft.discordbotmanager.sandbox.Status;
import net.vitacraft.discordbotmanager.utils.TimeConverter;
import java.util.List;
import java.util.Map;

public class CommandInterpreter {

    private final SandboxManager sbm;

    public CommandInterpreter(SandboxManager sandboxManager) {
        this.sbm = sandboxManager;
    }

    public String interpret(List<String> args) {
        String response = "An unknown error has occurred";
        if (args.isEmpty()) {
            response = ("§bDBM is running Version 1.0");
            return response;
        }
        String name = args.get(1);
        switch (args.getFirst().toLowerCase()) {
            case "help":
                response = """
                        §b§lDiscord Bot Manager
                        §3+------------------------+
                        §e/dbm help §7- Show this help message
                        §e/dbm list §7- List all sandboxes
                        §e/dbm init <file> <name> §7- Initialize a new sandbox
                        §e/dbm start <name> §7- Start a sandbox
                        §e/dbm stop <name> §7- Stop a sandbox
                        §e/dbm view <name> §7- View details of a sandbox""";
                break;
            case "list":
                StringBuilder builder = new StringBuilder();
                builder.append("§b§lDiscord Bot Manager\n");
                builder.append("§3+--------------------+\n");
                Map<String, Sandbox> sandboxes = sbm.getAllSandboxes();
                int count = 0;
                for (Sandbox sandbox : sandboxes.values()){
                    builder.append("§e[").append(sandbox.getSettings().name()).append("]").append(" ");
                    if (sandbox.getStatus() == Status.RUNNING){
                        builder.append("§eRunning - ").append(TimeConverter.millisecondsToPretty(System.currentTimeMillis() - sandbox.getStartTime()));
                    } else if (sandbox.getStatus() == Status.STOPPED){
                        builder.append("§cStopped");
                    } else {
                        builder.append("§4Error");
                    }
                    if (count < sandboxes.size() - 1){
                        builder.append("\n");
                    }
                    count++;
                }
                response = builder.toString();
                break;
            case "init":
                String file = args.get(1);
                String nameToBe = args.get(2);
                if (nameToBe == null || nameToBe.isEmpty()) {
                    nameToBe = file.toLowerCase().substring(0, file.length() - 4);
                }
                sbm.registerSandbox(new Sandbox(nameToBe, file));
                break;
            case "start":
                if (sbm.startSandbox(name)){
                    response = "§e[" + name + "] §aSuccessfully started";
                } else {
                    response = "§b[DBM] §cUnable to find Sandbox with name: §b" + name;
                }
                break;
            case "stop":
                if (sbm.stopSandbox(name)){
                    response = "§e[" + name + "] §aSuccessfully started";
                } else {
                    response = "§b[DBM] §cUnable to find running Sandbox with name: §b" + name;
                }
                break;
            case "view":
                throw new IllegalArgumentException("view");
            default:
                response = "§c/dbm help";
        }
        return response;
    }
}
