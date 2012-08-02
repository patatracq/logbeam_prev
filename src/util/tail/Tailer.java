/*
 * log-beam is a platform independent and distributed log monitoring tool.
 * 
 * Copyright (C) 2011  Jonas Stråle
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contact:       jonas.strale@gmail.com
 * Project home:  http://code.google.com/p/log-beam/
 */

package util.tail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Tailer implements Runnable {
	
	private String filename;
	private TailListener listener;
	private long delay;

	public Tailer( String filename, TailListener listener, long delay ) {
		this.filename = filename;
		this.listener = listener;
		this.delay = delay;
	}

	public void run() {
		
		long position = 0;
		try {
			while ( true ) {
				File configuratedFile = new File( filename );
				File file = FileFinder.latest( FileFinder.beginsWith( configuratedFile.getParentFile(), configuratedFile.getName() ) );
	
				if ( file == null ) {
					throw new FileNotFoundException( filename );
				} else {
					RandomAccessFile reader = new RandomAccessFile( file, "r" );
					
					if ( reader.length() > 0 ) {
						position = reader.length() - 1;
					} else {
						position = 0;
					}
					
					while ( true ) {
						
						if ( newerFileExists( file ) ) {
							break;
						}
						
						if ( reader.length() < position ) {
							position = 0;
						}
						
						reader.seek( position );
		
						if ( reader.length() > position ) {
							StringBuffer line = new StringBuffer();
							while ( reader.length() > position ) {
								char nextChar = (char) reader.read();
								position = reader.getFilePointer();
								if ( nextChar != '\r' ) {
									if ( nextChar == '\n' ) {
										if ( !line.toString().equals( "" ) )
										listener.newLine( line.toString() );
										line = new StringBuffer();
									} else {
										line.append( nextChar );
									}
								}
							}
							
							if ( line.length() > 0 ) {
								position = position - line.length();
							}
						}
						
						try {
							Thread.sleep( delay );
						} catch ( InterruptedException ie ) {
							// No action
						}
					}
					
				}

				try {
					Thread.sleep( delay );
				} catch ( InterruptedException ie ) {
					// No action
				}
			}
		} catch ( IOException ioe ) {
			listener.exception( ioe );
		} catch ( Exception e ) {
			listener.exception( e );
		}
	}
	
	private boolean newerFileExists( File file ) {
		
		File configuratedFile = new File( filename );
		File prospect = FileFinder.latest( FileFinder.beginsWith( file.getParentFile(), configuratedFile.getName() ) );
		return ( prospect.lastModified() > file.lastModified() );
	}
}
