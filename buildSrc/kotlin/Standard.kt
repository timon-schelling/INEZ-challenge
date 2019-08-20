import org.gradle.api.Project
import util.dependsOnSubProjects
import util.useProjectDefaultKotlinSourceSet

fun Project.defaultProject() {
    useProjectDefaultKotlinSourceSet()
    dependsOnSubProjects()
}
