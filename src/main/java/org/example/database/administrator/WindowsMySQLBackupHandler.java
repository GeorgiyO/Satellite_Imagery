package org.example.database.administrator;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.Arrays;

public class WindowsMySQLBackupHandler implements BackupHandler {

    @Value("${spring.datasource.dbname}")
    private String dbName;
    @Value("${spring.datasource.username}")
    private String dbUser;
    @Value("${spring.datasource.password}")
    private String dbPass;
    @Value("${spring.datasource.backup.filename}")
    private String fileName;
    @Value("${spring.datasource.mysql.bin}")
    private String mysqlPath;

    @Override
    public void createBackup() throws IOException, InterruptedException, URISyntaxException, CommandNotExecuteException {
        /*NOTE: Getting path to the Jar file being executed*/
        /*NOTE: YourImplementingClass-> replace with the class executing the code*/
        CodeSource codeSource = this.getClass().getProtectionDomain().getCodeSource();
        File jarFile = new File(codeSource.getLocation().toURI().getPath());
        String jarDir = jarFile.getParentFile().getPath();

        /*NOTE: Creating Path Constraints for folder saving*/
        /*NOTE: Here the backup folder is created for saving inside it*/
        String folderPath = jarDir + "/backup";

        /*NOTE: Creating Folder if it does not exist*/
        File f1 = new File(folderPath);
        f1.mkdir();

        /*NOTE: Creating Path Constraints for backup saving*/
        /*NOTE: Here the backup is saved in a folder called backup with the name backup.sql*/
        String savePath = "\"" + jarDir + "/backup/" + fileName + "\"";

        /*NOTE: Used to create a cmd command*/
        String executeCmd = String.format("\"%smysqldump\" -u%s -p%s %s -r %s", mysqlPath, dbUser, dbPass, dbName, savePath);

        /*NOTE: Executing the command here*/
        Process runtimeProcess = Runtime.getRuntime().exec(String.join("", executeCmd));
        int processComplete = runtimeProcess.waitFor();

        /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
        if (processComplete == 0) {
            LoggerFactory.getLogger(this.getClass()).info("creating backup: Complete");
        } else {
            LoggerFactory.getLogger(this.getClass()).info("creating backup: Failure");
            throw new CommandNotExecuteException("cant create backup: " + executeCmd);
        }
    }

    @Override
    public void restoreFromBackup() throws InterruptedException, IOException, URISyntaxException, CommandNotExecuteException {
        /*NOTE: String fileName is the mysql file name including the .sql in its name*/
        /*NOTE: Getting path to the Jar file being executed*/
        /*NOTE: YourImplementingClass-> replace with the class executing the code*/
        CodeSource codeSource = this.getClass().getProtectionDomain().getCodeSource();
        File jarFile = new File(codeSource.getLocation().toURI().getPath());
        String jarDir = jarFile.getParentFile().getPath();

        /*NOTE: Creating Path Constraints for restoring*/
        String restorePath = jarDir + "/backup/" + fileName;

        /*NOTE: Used to create a cmd command*/

        String executeCkmd = String.format("\"%smysql\" %s -u%s -p%s -e source %s", mysqlPath, dbName, dbUser, dbPass, restorePath);

        String[] executeCmd = new String[]{
                "\"" + mysqlPath + "mysql\"",
                dbName,
                "-u" + dbUser,
                "-p" + dbPass,
                "-e",
                " source " + restorePath
        };

        LoggerFactory.getLogger(this.getClass()).info(Arrays.toString(executeCmd));

        /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
        Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runtimeProcess.getInputStream()));
        bufferedReader.lines().forEach((line) -> {
            LoggerFactory.getLogger(this.getClass()).info("command output: " + line);
        });

        int processComplete = runtimeProcess.waitFor();

        /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
        if (processComplete == 0) {
            LoggerFactory.getLogger(this.getClass()).info("restoring database: Successful restoring from SQL : " + fileName);
        } else {
            LoggerFactory.getLogger(this.getClass()).info("restoring database: Error at restoring from SQL : " + fileName);
            throw new CommandNotExecuteException("cant restore database: " + processComplete);
        }
    }

    static class CommandNotExecuteException extends Exception {
        CommandNotExecuteException(String msg) {
            super(msg);
        }
    }
}
