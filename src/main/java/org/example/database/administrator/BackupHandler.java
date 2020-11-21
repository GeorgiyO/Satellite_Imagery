package org.example.database.administrator;

import java.io.IOException;
import java.net.URISyntaxException;

public interface BackupHandler {
    void createBackup() throws IOException, InterruptedException, URISyntaxException, WindowsMySQLBackupHandler.CommandNotExecuteException;
    void restoreFromBackup() throws InterruptedException, IOException, URISyntaxException, WindowsMySQLBackupHandler.CommandNotExecuteException;
}
