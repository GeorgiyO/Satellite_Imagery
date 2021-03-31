package org.example.controller.administrator;

import org.example.controller.template.Fragment;
import org.example.database.administrator.BackupHandler;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrator/backup")
public class BackupController {

    @Autowired
    private BackupHandler backupHandler;

    @GetMapping()
    public String backupPage(Model model) {
        return "adm/backup";
    }
    @GetMapping("/ajax")
    public String backupPageAjax(Model model) {
        return Fragment.get("adm/backup");
    }

    @GetMapping("/create")
    public String createBackupPage() {
        return "adm/backup-create";
    }
    @GetMapping("/create/ajax")
    public String createBackupPageAjax(Model model) {
        return Fragment.get("adm/backup-create");
    }

    @GetMapping("/restore")
    public String restoreDatabasePage() {
        return "adm/backup-restore";
    }
    @GetMapping("/restore/ajax")
    public String restoreDatabasePageAjax(Model model) {
        return Fragment.get("adm/backup-restore");
    }


    @PostMapping("/create")
    public String createBackup(Model model) {
        LoggerFactory.getLogger(this.getClass()).info("creating backup...");
        try {
            backupHandler.createBackup();
            model.addAttribute("succeed", true);
        } catch (Exception e) {
            model.addAttribute("succeed", false);
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
        }
        return Fragment.get("adm/backup-create-result");
    }

    @PostMapping("/restore")
    public String restoreDatabase(Model model) {
        LoggerFactory.getLogger(this.getClass()).info("restoring database...");
        try {
            backupHandler.restoreFromBackup();
            model.addAttribute("succeed", true);
        } catch (Exception e) {
            model.addAttribute("succeed", false);
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
        }
        return Fragment.get("adm/backup-restore-result");
    }

}
