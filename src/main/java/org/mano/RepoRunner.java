package org.mano;

public class RepoRunner {

    public static void main(String s[]){
        RepoTargetFactory repoTarget = new NexusRepository();
        repoTarget.setRepoConfigurations("http://localhost:8081/service/rest/v1/components?repository=myownrepo", "admin", "admin");
        repoTarget.pushToRepository();
    }

}
