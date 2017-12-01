package com.demo.vod.util.ssh2;

import com.jcraft.jsch.SftpProgressMonitor;

public class FileProgressMonitor implements SftpProgressMonitor {

	@Override
	public void init(int op, String src, String dest, long max) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean count(long count) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
