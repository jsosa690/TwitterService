.PHONY: archive clean

all: archive

archive:
	OLD_CWD=$${PWD##*/} && cd .. && zip -vr $${OLD_CWD}/$$(date +'%y%m%d_%H%M%S')_platform_homework.zip $${OLD_CWD} -x $${OLD_CWD}/target/\* $${OLD_CWD}/*_platform_homework.zip $${OLD_CWD}/.\*

clean:
	rm -f *_platform_homework.zip
