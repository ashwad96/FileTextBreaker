#!c:\users\aswadhwa\appdata\local\programs\python\python37-32\python.exe
# EASY-INSTALL-ENTRY-SCRIPT: 'chapterize==0.1.1','console_scripts','chapterize'
__requires__ = 'chapterize==0.1.1'
import re
import sys
from pkg_resources import load_entry_point

if __name__ == '__main__':
    sys.argv[0] = re.sub(r'(-script\.pyw?|\.exe)?$', '', sys.argv[0])
    sys.exit(
        load_entry_point('chapterize==0.1.1', 'console_scripts', 'chapterize')()
    )
