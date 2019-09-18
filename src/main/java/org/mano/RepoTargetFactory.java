package org.mano;

public interface RepoTargetFactory {

    void setRepoConfigurations(String repoUrl, String userName, String password);
    String pushToRepository();
}
