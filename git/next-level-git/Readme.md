# [Next Level Git - Master your workflow | Live Training](https://www.safaribooksonline.com/live-training/courses/next-level-git-master-your-workflow/0636920190233/)
- [Next Level Git - Master your workflow | Live Training](#next-level-git---master-your-workflow--live-training)
	- [Resources](#resources)
	- [Reset](#reset)
	- [Revert](#revert)
	- [Grep](#grep)
	- [Bisect](#bisect)
	- [Worktrees](#worktrees)
	- [Submodules](#submodules)
	- [Subtrees](#subtrees)
	- [Hooks](#hooks)

## Resources
* [Professional Git](https://www.safaribooksonline.com/library/view/professional-git/9781119284970/)

## Reset
* Allows to roll back branch to a previous commit
	* optionally roll back staging area and working dir

## Revert
* allows to undo as well
* but adds a new change/commit
	* to denotes changes being reverted
* use `revert` when the changes to be reverted has been pushed to remote
	* else use `reset`

## Grep
* search using regex

## Bisect
* binary search through git's history to find a problem commit
* start with `git bisect start`
	* can pass a range `git bisect start HEAD HEAD~10`
* identify a bad commit with `git bisect bad`
* identify a good commit with `git bisect good`
* useful options to bisect
	* log, visualize, reset, skip, run
	* `run` allows to automate using scripts
	* `git bisect reset refs/bisect/bad^`
		* reset to one commit before the last bad commit

## Worktrees
* allows multiple separate working areas attached to one local repo
* use case: simultaneous development in multiple branches
* `git worktree add -b <branch> <target dir>`
	* * checkouts the specified branch to the target dir

## Submodules
* Allows including a separate repository with your current repository
* Use case - include the Git repository for one or more dependencies along with the
original repository for a project

## Subtrees
* Allows including a copy of a separate repository with your current repository
* Use case - include a copy of a Git repository for one or
more dependencies along with the original repository for
a project
* vs submodule
	* no links - just a copy
	* advantage - no links to maintain
	* disadvantage - extra content added to project
* `squash` option to compress history from remote before adding it
* `git subtree pull|merge ...`
* `split` - extract subproject content into a sep branch

## Hooks
* a program or script that runs when a certain event happens
* Use cases
	* Validating that certain conventions have been met before a commit
	* Appending items to commit messages
	* Checking the format or existence of certain elements in a commit message
	* Updating content in the working directory after an operation
	* Enforcing coding standards
* sample hook templates are part of git installation
* hooks are not cloned
	* but `core.hooksPath` config can be updated to point to a common hook location
	* `git init` would refresh hooks from the location
* return code of hook script
	* 0 - continue operation
	* non-zero - abort operation
* env vars avail to hooks
	* GIT_DIR, GIT_AUTHOR_DATE, GIT_AUTHOR_EMAIL, GIT_AUTHOR_NAME
* hook events
	* commit
	* push
	* rebase
	* merge
	* pull
	* checkout
