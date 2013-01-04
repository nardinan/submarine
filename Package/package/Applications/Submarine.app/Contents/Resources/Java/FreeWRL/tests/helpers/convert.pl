my $url = "test.gif";
my $status = system ("/usr/X11R6/bin/convert $url /tmp/test.jpg");
die "$program exited because of an image conversion problem: $?"
	unless $status == 0;

print "$status \n";
