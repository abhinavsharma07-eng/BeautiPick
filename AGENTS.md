# AGENTS.md

Instructions for every AI coding agent working in this repository.
If a rule conflicts with a user request, stop and ask before proceeding.

## Project
- Type: Java web application, built with Maven, packaged as a WAR (`<packaging>war</packaging>`)
- Java version: `26` (do not change)
- Server / container: ` Tomcat 10.1.6`
- Build: `mvn clean package` (output: `target/*.war`)
- Run tests: `mvn test`
- Compile only: `mvn compile`
- Run a single test: `mvn -Dtest=ClassName test`

## Structure (do not reorganize)
- `src/main/java`      Java source
- `src/main/resources` config and properties
- `src/main/webapp`    web content (JSP, HTML, JS, CSS, `WEB-INF/`)
- `src/test/java`      tests
- `pom.xml`            build definition

## Scope
- Do ONLY what the current task asks. Nothing extra.
- No unrequested refactors, renames, reformatting, or import reordering.
- No new features, abstractions, or frameworks unless explicitly asked.
- Touch the fewest files possible. Keep diffs small.

## Before changing anything
- Read the relevant files first. Do not guess class names, methods, or APIs.
- If the task is ambiguous, ask ONE clear question instead of assuming.
- Verify that a library or method exists in the project's current dependency versions before using it.
- Do not use APIs newer than the project's Java version.

## Forbidden without explicit approval
- Editing `pom.xml`: dependencies, plugin versions, Java version, packaging type, groupId/artifactId/version, profiles
- Adding, upgrading, or removing Maven dependencies
- Editing `src/main/webapp/WEB-INF/web.xml`, `context.xml`, or server config
- Editing `application.properties`/environment config with credentials, URLs, or ports
- Changing database schemas, SQL scripts, or migrations
- Changing public servlet URLs, REST endpoints, or method signatures used elsewhere
- Deleting files or directories
- Committing or editing anything in `target/`, `.git/`, or IDE folders
- Destructive commands (`rm -rf`, `git reset --hard`, `git push --force`)
- Committing, pushing, merging, or deploying the WAR
- Anything under: `<off-limits paths>`

## Allowed freely
- Reading files and searching the codebase
- Running `mvn compile`, `mvn test`, `mvn clean package`
- Editing only the Java, JSP, or resource files directly related to the task

## Code style
- Match the existing style, naming, package structure, and indentation.
- Follow Java naming conventions already used in the project.
- No placeholder code, empty catch blocks, `printStackTrace()`, or fake data presented as finished work.
- Use the project's existing logging approach, not `System.out.println`.
- Always close resources (try-with-resources) and handle exceptions as nearby code does.
- Use parameterized queries (`PreparedStatement`) only. Never build SQL with string concatenation.
- Never hardcode passwords, API keys, or connection strings.

## Verification
- After any code change, run `mvn clean package` and confirm `BUILD SUCCESS` and that the WAR is produced.
- Run `mvn test` when tests exist for the changed code.
- Never claim something works unless you ran it and saw it work.

## Honesty
- If a build or test fails, or you could not verify something, say so plainly.
- If the request is wrong, risky, or a bad idea, say so directly. Do not agree just to please.
- Report mistakes openly and fix them.

## When finished
Give a short summary:
1. Files changed
2. What changed and why
3. Build and test result (`mvn` output summary)
4. Anything unverified or left undone